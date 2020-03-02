package me.aerclient.implementation.module.base;

import me.aerclient.implementation.module.modules.Dummy;
import me.aerclient.implementation.module.modules.movement.*;
import me.aerclient.implementation.module.modules.render.*;
import me.aerclient.implementation.module.modules.world.*;
import me.aerclient.implementation.module.modules.combat.*;
import me.aerclient.implementation.module.modules.exploit.*;
import me.aerclient.implementation.module.modules.player.*;
import me.aerclient.implementation.module.modules.util.*;

import me.aerclient.config.valuesystem.ValueManager;

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
		if (getModuleByName(name).isActive()) {
			return true;
		}
		return false;
	}


}
