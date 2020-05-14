package me.aer.implementation.module.base;

import me.aer.config.valuesystem.ValueManager;
import me.aer.implementation.module.modules.Dummy;
import me.aer.implementation.module.modules.combat.Aura;
import me.aer.implementation.module.modules.exploit.Ultraspeed;
import me.aer.implementation.module.modules.movement.ClickTeleport;
import me.aer.implementation.module.modules.movement.Flight;
import me.aer.implementation.module.modules.movement.Sprint;
import me.aer.implementation.module.modules.player.Freecam;
import me.aer.implementation.module.modules.player.NoFall;
import me.aer.implementation.module.modules.player.Scaffold;
import me.aer.implementation.module.modules.render.Brightness;
import me.aer.implementation.module.modules.render.StorageESP;
import me.aer.implementation.module.modules.render.Tracers;
import me.aer.implementation.module.modules.render.XRay;
import me.aer.implementation.module.modules.util.ClickGui;
import me.aer.implementation.module.modules.util.HUD;
import me.aer.implementation.module.modules.world.Timer;

import java.util.ArrayList;


/**
 * Handles loading and getting modules
 */
public class ModuleManager {

	public static ArrayList<Module> moduleList = new ArrayList<Module>();
	public static ArrayList<Module> visibleModuleList = new ArrayList<Module>();


	/**
	 * Loads all modules into this ModuleManager, and loads the settings for each of those modules
	 */
	public static void init() {
        loadModules();
        for (Module module : moduleList) {
            ValueManager.registerObject(module.getName(), module);
            if (module.getCategory() != Category.HIDDEN) {
                visibleModuleList.add(module);
            }
        }
        ValueManager.loadValues();
        for (Module module : moduleList) {
            module.setup();
        }
    }

	/**
	 * Loads all modules into this ModuleManager
	 */
	public static void loadModules() {
		//Combat
		moduleList.add(new Aura());
		//Movement
		moduleList.add(new Flight());
		moduleList.add(new ClickTeleport());
		moduleList.add(new Sprint());
		//Render
		moduleList.add(new Brightness());
		moduleList.add(new StorageESP());
		moduleList.add(new Tracers());
		moduleList.add(new XRay());
		//World
		moduleList.add(new Timer());
		//Player
		moduleList.add(new Freecam());
		moduleList.add(new NoFall());
		moduleList.add(new Scaffold());
		//Exploit
		moduleList.add(new Ultraspeed());
		//Utils
		moduleList.add(new HUD());
		moduleList.add(new ClickGui());
		moduleList.add(new Dummy());
		//Hidden


		//
	}

	/**
	 * Returns all modules that are active (on)
	 */
	public static ArrayList<Module> getActiveModules() {
		ArrayList<Module> activeModules = new ArrayList<Module>();
		for (Module module : moduleList) {
			if (module.isActive()) {
				activeModules.add(module);
			}
		}
		return activeModules;
	}

	/**
	 * Returns the number of modules that are loaded
	 */
	public static int getModuleNum() {
		int i = 0;
		for (Module module : moduleList) {
			i++;
		}
		return i;
	}

	public static void add(Module m) {
		moduleList.add(m);
		if (m.getCategory() != Category.HIDDEN) {
			visibleModuleList.add(m);
		}

	}

	public static Module getModuleByName(String string) {
		for (Module m : moduleList) {
			if (m.getName().equals(string)) {
				return m;
			}
		}
		return null;
	}

	public static boolean isModuleActive(String name) {
        return getModuleByName(name).isActive();
    }


}
