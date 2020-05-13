package me.aer.implementation.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;
import me.aer.config.valuesystem.BooleanValue;
import me.aer.config.valuesystem.ModeValue;
import me.aer.config.valuesystem.NumberValue;
import me.aer.implementation.module.base.Category;
import me.aer.implementation.module.base.Module;
import me.aer.implementation.utils.world.TimerUtil;
import me.aer.injection.events.entity.EventPreEntityUpdate;
import me.aer.injection.events.world.EventPreUpdate;

public class Flight extends Module {

	private NumberValue<Float> speed = new NumberValue<>("Speed", "How fast you move, in idgaf/second", 0.5f, 0f, 10f, false);
	private ModeValue mode = new ModeValue("Mode", "Flight mode", "Vanilla", "Vanilla", "Freeze");
	private BooleanValue antiKick = new BooleanValue("Anti-Kick", "Attempts to prevent the \"Flying is not enabled\" kick", true);
	private BooleanValue antiDamage = new BooleanValue("Anti-Damage",
			"Attempts to prevent you taking fall damage when landing. You may still take damage if you go down too fast.",
			true);
	private TimerUtil kickTimer = new TimerUtil();


    public Flight() {
		super("Flight", Category.MOVEMENT, "Allows you to fly.");
	}

	@EventTarget
    public void eventPreUpdate(EventPreUpdate event) {

        if (antiDamage.getObject()) {
            clipUtil.Clip(0, 0, false, false);
        }
        if (antiKick.getObject()) {
            if ((kickTimer.delay(500) && minecraft.player.posY < 290D) || kickTimer.delay(100) && minecraft.player.posY > 290D) {
                antiKickUtil.resetVanilla();
                kickTimer.reset();
            }
        }

        if (mode.getValue().equalsIgnoreCase("Vanilla")) {
            minecraft.player.capabilities.isFlying = true;
            minecraft.player.capabilities.allowFlying = true;
            minecraft.player.capabilities.setFlySpeed(speed.getValue() / 10);
            minecraft.player.setSpeedInAir(speed.getValue() / 2);
        } else if (mode.getValue().equalsIgnoreCase("Freeze")) {
            minecraft.player.capabilities.isFlying = false;
            minecraft.player.setVelocity(0, 0, 0);
            minecraft.player.setSpeedInAir(speed.getValue() / 2);
		}

		minecraft.player.inWater = false;

	}

	@EventTarget
	public void onPreEntityLivingUpdate(EventPreEntityUpdate event) {

        if (mode.getValue().equalsIgnoreCase("Freeze")) {
            if (minecraft.player.movementInput.jump) {
                minecraft.player.motionY = speed.getValue() / 2;
            }
            if (minecraft.player.movementInput.sneak) {
                minecraft.player.motionY = speed.getValue() - (speed.getValue() * 1.5);
            }
            minecraft.player.movementInput.jump = false;
            minecraft.player.movementInput.sneak = false;
        }
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
