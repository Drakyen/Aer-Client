package me.aerclient.implementation.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;
import com.mojang.authlib.GameProfile;
import me.aerclient.injection.events.net.EventPacketSent;
import me.aerclient.injection.events.world.EventPostUpdate;
import me.aerclient.injection.events.entity.EventPreEntityUpdate;
import me.aerclient.injection.events.world.EventPreUpdate;
import me.aerclient.implementation.module.base.Category;
import me.aerclient.implementation.module.base.Module;
import me.aerclient.config.valuesystem.BooleanValue;
import me.aerclient.config.valuesystem.NumberValue;
import me.aerclient.implementation.utils.world.WorldUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;

public class Freecam extends Module {

	private BooleanValue Sticky = new BooleanValue("StickyFly", true);
	private BooleanValue CancelPackets = new BooleanValue("CancelPackets", true);
	private NumberValue Speed = new NumberValue("Speed", 0.5f, 0f, 10f, false);
	private EntityOtherPlayerMP fakePlayer;
	private float posY;
	private float posX;
	private float posZ;
	private float DefaultFlightSpeed;

	public Freecam() {
		super("Freecam", Category.PLAYER, "Allows you fly outside your body");
	}


	public void onValueUpdate() {
		minecraft.player.capabilities.setFlySpeed(DefaultFlightSpeed);
	}


	public void onEnable() {
		DefaultFlightSpeed = minecraft.player.capabilities.getFlySpeed();
		this.posX = (float) minecraft.player.posX;
		this.posY = (float) minecraft.player.posY;
		this.posZ = (float) minecraft.player.posZ;
		(this.fakePlayer = new EntityOtherPlayerMP(minecraft.world, new GameProfile(minecraft.session.getProfile().getId(), minecraft.player.getName()))).setPositionAndRotation(minecraft.player.posX, minecraft.player.posY, minecraft.player.posZ, minecraft.player.rotationYaw, minecraft.player.rotationPitch);
		this.fakePlayer.rotationYawHead = minecraft.player.rotationYawHead;
		this.fakePlayer.inventory = minecraft.player.inventory;
		this.fakePlayer.setSneaking(minecraft.player.isSneaking());
		minecraft.world.addEntityToWorld(1337, this.fakePlayer);
		minecraft.player.pushOutOfBlocks = false;
		minecraft.renderGlobal.loadRenderers();
		WorldUtils.setCulling(false, true);
	}


	public void onDisable() {
		try {
			if (this.fakePlayer != null) {
				minecraft.world.removeEntity(this.fakePlayer);
				minecraft.player.setPosition(this.posX, this.posY, this.posZ);
			}
		} catch (Exception ex) {
		}
		minecraft.player.capabilities.isFlying = false;
		minecraft.player.pushOutOfBlocks = true;
		minecraft.player.capabilities.setFlySpeed(DefaultFlightSpeed);
		WorldUtils.setCulling(true, false);
	}

	@EventTarget
	public void eventPreUpdate(EventPreUpdate event) {
		minecraft.player.capabilities.isFlying = true;
		minecraft.player.inWater = false;
		minecraft.player.onGround = false;
		minecraft.player.capabilities.setFlySpeed(Speed.getValue().floatValue() / 5);
		minecraft.player.inWater = false;

	}

	@EventTarget
	public void eventPreEntityUpdate(EventPreEntityUpdate event) {
		minecraft.player.noClip = true;
	}


	@EventTarget
	public void eventPacketSent(EventPacketSent event) {
		if (!CancelPackets.getObject()) {
			return;
		}
		Packet p = event.packet;
		if (p instanceof CPacketPlayer || p instanceof CPacketPlayerAbilities || p instanceof CPacketInput) {
			event.cancel();
		} else if (p instanceof CPacketEntityAction) {
			CPacketEntityAction p1 = (CPacketEntityAction) p;
			if (p1.getAction().equals(CPacketEntityAction.Action.START_SNEAKING) ||
					p1.getAction().equals(CPacketEntityAction.Action.STOP_SNEAKING) ||
					p1.getAction().equals(CPacketEntityAction.Action.START_SPRINTING) ||
					p1.getAction().equals(CPacketEntityAction.Action.STOP_SPRINTING)
			) {

				event.cancel();
			}
		}
		return;
	}

	@EventTarget
	public void eventPostUpdate(EventPostUpdate event) {
		if (minecraft.player.movementInput.forwardKeyDown || minecraft.player.movementInput.backKeyDown ||
				minecraft.player.movementInput.leftKeyDown || minecraft.player.movementInput.rightKeyDown ||
				minecraft.player.movementInput.jump || minecraft.player.movementInput.sneak) {
		} else if (Sticky.getObject()) {
			minecraft.player.setVelocity(0, 0, 0);
		}
	}

}
