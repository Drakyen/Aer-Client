package net.aer.module.modules.player;

import com.darkmagician6.eventapi.EventTarget;
import net.aer.events.net.EventPacketSent;
import net.aer.module.base.Category;
import net.aer.module.base.Module;
import net.aer.utils.valuesystem.ModeValue;
import net.minecraft.network.play.client.CPacketPlayer;

public class NoFall extends Module {

	private ModeValue Mode = new ModeValue("Mode", "Packet", new String[]{"Packet"});

	public NoFall() {
		super("NoFall", Category.PLAYER, "Prevents you from taking fall damage");
	}


	@EventTarget
	public void eventPacketSent(EventPacketSent ep) {
		if (Mode.getValue().equalsIgnoreCase("Packet")) {
			if (ep.packet instanceof CPacketPlayer && minecraft.player.fallDistance >= 2.0f) {
				CPacketPlayer packet = (CPacketPlayer) ep.packet;
				packet.onGround = true;
			}
		}
	}

}
