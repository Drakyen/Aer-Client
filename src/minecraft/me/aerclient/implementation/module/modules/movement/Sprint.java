package me.aerclient.implementation.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;
import me.aerclient.implementation.module.base.Category;
import me.aerclient.implementation.module.base.Module;
import me.aerclient.injection.events.world.EventPreUpdate;


/**
 * Forcefully toggles sprinting on when active
 */
public class Sprint extends Module {

	/**
	 * Constructor for Sprint
	 */
	public Sprint() {
        super("Sprint", Category.MOVEMENT, "Permanently toggles sprinting");

    }

	@EventTarget
	public void preUpdate(EventPreUpdate event) {
		minecraft.player.setSprinting(true);
	}

	public void onDisable() {
		minecraft.player.setSprinting(false);
	}

}
