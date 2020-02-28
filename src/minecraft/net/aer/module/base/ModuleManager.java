package net.aer.module.base;

import net.aer.gui.TestingGui;
import net.aer.module.base.Category;
import net.aer.module.base.Module;
import net.aer.module.modules.Dummy;
import net.aer.module.modules.combat.Aura;
import net.aer.module.modules.exploit.Ultraspeed;
import net.aer.module.modules.movement.ClickTeleport;
import net.aer.module.modules.movement.Flight;
import net.aer.module.modules.movement.Freecam;
import net.aer.module.modules.movement.Sprint;
import net.aer.module.modules.player.NoFall;
import net.aer.module.modules.render.Brightness;
import net.aer.module.modules.render.StorageESP;
import net.aer.module.modules.render.Tracers;
import net.aer.module.modules.render.XRay;
import net.aer.module.modules.util.ClickGui;
import net.aer.module.modules.util.HUD;
import net.aer.utils.valuesystem.ValueManager;

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

	public static void onExit() {
		for (Module m : moduleList) {
			m.onExit();
		}

	}


}
