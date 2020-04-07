package me.aer.implementation.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;
import me.aer.config.valuesystem.BooleanValue;
import me.aer.config.valuesystem.ModeValue;
import me.aer.config.valuesystem.NumberValue;
import me.aer.implementation.module.base.Category;
import me.aer.implementation.module.base.Module;
import me.aer.implementation.utils.world.TimerUtil;
import me.aer.injection.events.entity.EventPreEntityUpdate;
import me.aer.injection.events.net.EventPacketSent;
import me.aer.injection.events.world.EventPreTick;
import me.aer.injection.events.world.EventPreUpdate;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;

public class Flight extends Module {

	private NumberValue<Float> speed = new NumberValue<>("Speed", "How fast you move, in idgaf/second", 0.5f, 0f, 10f, false);
	private ModeValue mode = new ModeValue("Mode", "Flight mode", "Vanilla", "Vanilla", "Freeze");
	private BooleanValue antiKick = new BooleanValue("Anti-Kick", "Attempts to prevent the \"Flying is not enabled\" kick", true);
	private BooleanValue antiDamage = new BooleanValue("Anti-Damage",
			"Attempts to prevent you taking fall damage when landing. You may still take damage if you go down too fast.",
			true);
	private double flyHeight;
	private TimerUtil kickTimer = new TimerUtil();


	public Flight() {
		super("Flight", Category.MOVEMENT, "Allows you to fly.");
	}


	@EventTarget
    public void eventPreUpdate(EventPreUpdate event) {

		if (antiDamage.getObject()) {
			clipUtil.Clip(0, 0, false, false);
		}

		if (mode.getValue().equalsIgnoreCase("Vanilla")) {
			minecraft.player.capabilities.isFlying = true;
			minecraft.player.capabilities.setFlySpeed(speed.getValue() / 10);
			minecraft.player.setSpeedInAir(speed.getValue() / 2);
		} else if (mode.getValue().equalsIgnoreCase("Freeze")) {
			minecraft.player.capabilities.isFlying = false;
			minecraft.player.setVelocity(0, 0, 0);
			minecraft.player.setSpeedInAir(speed.getValue() / 2);
		}

		minecraft.player.inWater = false;

		if (antiKick.getObject()) {
			updateFlyHeight();
			if (((this.flyHeight <= 290.0D) && (kickTimer.delay(500L)))
					|| ((this.flyHeight > 290.0D) && (kickTimer.delay(100L)))) {
				goToGround();
				kickTimer.reset();
			}
		}

	}

	public void updateFlyHeight() {
		double h = 1.0D;
		AxisAlignedBB box = minecraft.player.getEntityBoundingBox().expand(0.0625D, 0.0625D, 0.0625D);
		for (this.flyHeight = 0.0D; this.flyHeight < minecraft.player.posY; this.flyHeight += h) {
			AxisAlignedBB nextBox = box.offset(0.0D, -this.flyHeight, 0.0D);
			if (minecraft.world.checkBlockCollision(nextBox)) {
				if (h < 0.0625D) {
					break;
				}
				this.flyHeight -= h;
				h /= 2.0D;
			}
		}
	}

	public void goToGround() {
		if (this.flyHeight > 300.0D) {
			return;
		}
		double minY = minecraft.player.posY - this.flyHeight;
		if (minY <= 0.0D) {
			return;
		}
		for (double y = minecraft.player.posY; y > minY; ) {
			y -= 8.0D;
			if (y < minY) {
				y = minY;
			}
			CPacketPlayer.Position packet = new CPacketPlayer.Position(
					minecraft.player.posX, y, minecraft.player.posZ, true);
			minecraft.player.connection.sendPacket(packet);
		}
		for (double y = minY; y < minecraft.player.posY; ) {
			y += 8.0D;
			if (y > minecraft.player.posY) {
				y = minecraft.player.posY;
			}
			CPacketPlayer.Position packet = new CPacketPlayer.Position(
					minecraft.player.posX, y, minecraft.player.posZ, true);
			minecraft.player.connection.sendPacket(packet);
		}
	}

	@EventTarget
	public void onPreEntityLivingUpdate(EventPreEntityUpdate event) {

		if (mode.getValue().equalsIgnoreCase("Vanilla")) {
			minecraft.player.movementInput.jump = false;
			minecraft.player.movementInput.sneak = false;
			minecraft.player.capabilities.isFlying = true;
			minecraft.player.capabilities.allowFlying = true;
			minecraft.player.onGround = false;
		} else if (mode.getValue().equalsIgnoreCase("Freeze")) {
			if (minecraft.player.movementInput.jump) {
				minecraft.player.motionY = speed.getValue() / 2;
			}
			if (minecraft.player.movementInput.sneak) {
				minecraft.player.motionY = speed.getValue() - (speed.getValue() * 1.5);
			}
			minecraft.player.movementInput.jump = false;
			minecraft.player.movementInput.sneak = false;
			minecraft.player.capabilities.isFlying = false;
		}


	}

	@EventTarget
	public void tick(EventPreTick event) {

	}

	@EventTarget
	public void packet(EventPacketSent event) {
//    	if(antiDamage.getObject()) {
//			if (event.packet instanceof CPacketPlayer) {
//				((CPacketPlayer) event.packet).onGround = false;
//			}
//		}
	}


	public void onDisable() {
		minecraft.player.capabilities.isFlying = false;
		minecraft.player.capabilities.allowFlying = false;
		float defaultFlightSpeed = 0.05f;
		minecraft.player.capabilities.setFlySpeed(defaultFlightSpeed);
		float defaultWalkSpeed = 0.1f;
		minecraft.player.capabilities.setPlayerWalkSpeed(defaultWalkSpeed);
		float defaultSpeedInAir = 0.02f;
		minecraft.player.setSpeedInAir(defaultSpeedInAir);
		minecraft.player.setVelocity(0, 0, 0);
	}


}
