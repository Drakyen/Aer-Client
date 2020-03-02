package me.aerclient.implementation.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;
import me.aerclient.injection.events.entity.EventPreEntityUpdate;
import me.aerclient.injection.events.world.EventPreUpdate;
import me.aerclient.implementation.module.base.Category;
import me.aerclient.implementation.module.base.Module;
import me.aerclient.config.valuesystem.BooleanValue;
import me.aerclient.config.valuesystem.ModeValue;
import me.aerclient.config.valuesystem.NumberValue;
import me.aerclient.implementation.utils.world.TimerUtil;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;

public class Flight extends Module {

	private NumberValue Speed = new NumberValue("Speed", 0.5f, 0f, 10f, false);
	private ModeValue Mode = new ModeValue("Mode", "Vanilla", "Vanilla", "Freeze");
	private BooleanValue AntiKick = new BooleanValue("Anti-Kick", true);
	public float DefaultSpeedInAir = 0.02f;
	private float DefaultFlightSpeed = 0.05f;
	private float DefaultWalkSpeed = 0.1f;
	private double flyHeight;
	private TimerUtil.Timer kickTimer = new TimerUtil.Timer();


	public Flight() {
		super("Flight", Category.MOVEMENT, "Allows you to fly");
	}


	@EventTarget
	public void eventPreUpdate(EventPreUpdate event) {


		if (Mode.getValue().equalsIgnoreCase("Vanilla")) {
			minecraft.player.capabilities.isFlying = true;
			minecraft.player.setVelocity(0, 0, 0);
			minecraft.player.capabilities.setFlySpeed(Speed.getValue().floatValue() / 2);
		} else if (Mode.getValue().equalsIgnoreCase("Freeze")) {
			minecraft.player.capabilities.isFlying = false;
			minecraft.player.setVelocity(0, 0, 0);
			minecraft.player.setSpeedInAir(Speed.getValue().floatValue() / 2);
			minecraft.player.inWater = false;
		}

		if (AntiKick.getObject()) {
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
		if (Mode.getValue().equalsIgnoreCase("Vanilla")) {
			if (minecraft.player.movementInput.jump) {
				minecraft.player.motionY = Speed.getValue().floatValue() / 2;
			}
			if (minecraft.player.movementInput.sneak) {
				minecraft.player.motionY = Speed.getValue().floatValue() - (Speed.getValue().floatValue() * 1.5);
			}
			minecraft.player.movementInput.jump = false;
			minecraft.player.movementInput.sneak = false;
			minecraft.player.capabilities.isFlying = true;
		} else if (Mode.getValue().equalsIgnoreCase("Freeze")) {
			if (minecraft.player.movementInput.jump) {
				minecraft.player.motionY = Speed.getValue().floatValue() / 2;
			}
			if (minecraft.player.movementInput.sneak) {
				minecraft.player.motionY = Speed.getValue().floatValue() - (Speed.getValue().floatValue() * 1.5);
			}
			minecraft.player.movementInput.jump = false;
			minecraft.player.movementInput.sneak = false;
			minecraft.player.capabilities.isFlying = false;
		}
	}


	public void onDisable() {
		minecraft.player.capabilities.isFlying = false;
		minecraft.player.capabilities.setFlySpeed(DefaultFlightSpeed);
		minecraft.player.capabilities.setPlayerWalkSpeed(DefaultWalkSpeed);
		minecraft.player.setSpeedInAir(DefaultSpeedInAir);
		minecraft.player.setVelocity(0, 0, 0);
	}


}
