package me.aerclient.module.modules.player;

import com.darkmagician6.eventapi.EventTarget;
import me.aerclient.injection.events.net.EventPacketSent;
import me.aerclient.module.base.Category;
import me.aerclient.module.base.Module;
import me.aerclient.config.valuesystem.ModeValue;
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
