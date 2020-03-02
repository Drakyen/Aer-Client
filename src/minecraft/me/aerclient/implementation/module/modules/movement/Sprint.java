package me.aerclient.implementation.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;
import me.aerclient.injection.events.world.EventPreUpdate;
import me.aerclient.implementation.module.base.Category;
import me.aerclient.implementation.module.base.Module;


/**
 * Forcefully toggles sprinting on when active
 */
public class Sprint extends Module {

	/**
	 * Constructor for Sprint
	 */
	public Sprint() {
		super("Sprint", Category.MOVEMENT, "Permanantly toggles sprinting");

	}

	@EventTarget
	public void preUpdate(EventPreUpdate event) {
		minecraft.player.setSprinting(true);
	}

	public void onDisable() {
		minecraft.player.setSprinting(false);
	}

}
