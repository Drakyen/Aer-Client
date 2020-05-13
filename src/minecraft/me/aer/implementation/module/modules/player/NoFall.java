package me.aer.implementation.module.modules.player;

import com.darkmagician6.eventapi.EventTarget;
import me.aer.config.valuesystem.ModeValue;
import me.aer.implementation.module.base.Category;
import me.aer.implementation.module.base.Module;
import me.aer.injection.events.net.EventPacketSent;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;

import static net.minecraft.network.play.client.CPacketEntityAction.Action.START_FALL_FLYING;

public class NoFall extends Module {

	private ModeValue Mode = new ModeValue("Mode", "How to try and prevent you taking fall damage", "Packet", "Packet", "Prevent");

	public NoFall() {
		super("NoFall", Category.PLAYER, "Prevents you from taking fall damage");
	}

	private boolean fallFlying = false;

	@EventTarget
	public void eventPacketSent(EventPacketSent ep) {
		if (ep.packet instanceof CPacketEntityAction && ((CPacketEntityAction) ep.packet).getAction() == START_FALL_FLYING) {
			this.fallFlying = true;
		}
		if (ep.packet instanceof CPacketPlayer && ((CPacketPlayer) ep.packet).onGround) {
			this.fallFlying = false;
		}
		if (Mode.getValue().equalsIgnoreCase("Packet")) {
			if (ep.packet instanceof CPacketPlayer && minecraft.player.fallDistance >= 1.0f && !fallFlying) {
				CPacketPlayer packet = (CPacketPlayer) ep.packet;
				packet.onGround = true;
			}
		}
		if (Mode.getValue().equalsIgnoreCase("Prevent")) {
			if (ep.packet instanceof CPacketPlayer && minecraft.player.fallDistance > 0) {
				((CPacketPlayer) ep.packet).onGround = false;
				minecraft.player.fallDistance = 0;
			}
		}
	}



}
