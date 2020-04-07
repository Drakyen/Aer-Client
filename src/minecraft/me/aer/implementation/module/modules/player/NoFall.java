package me.aer.implementation.module.modules.player;

import com.darkmagician6.eventapi.EventTarget;
import me.aer.config.valuesystem.ModeValue;
import me.aer.implementation.module.base.Category;
import me.aer.implementation.module.base.Module;
import me.aer.injection.events.entity.EventPreEntityUpdate;
import me.aer.injection.events.net.EventPacketSent;
import net.minecraft.network.play.client.CPacketPlayer;

public class NoFall extends Module {

	private ModeValue Mode = new ModeValue("Mode", "How to try and prevent you taking fall damage", "Packet", "Packet", "Prevent");

	public NoFall() {
		super("NoFall", Category.PLAYER, "Prevents you from taking fall damage");
	}


	@EventTarget
	public void eventPacketSent(EventPacketSent ep) {
		if (Mode.getValue().equalsIgnoreCase("Packet")) {
			if (ep.packet instanceof CPacketPlayer && minecraft.player.fallDistance >= 1.0f) {
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

	@EventTarget
	public void preUpdate(EventPreEntityUpdate event) {

	}

}
