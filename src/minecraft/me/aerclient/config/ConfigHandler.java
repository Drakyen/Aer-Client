package me.aerclient.config;

import me.aerclient.Aer;
import me.aerclient.module.base.Module;
import me.aerclient.module.base.ModuleManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ConfigHandler {

	private static File configFile;


	/**
     * Saves a set of properties to the given file, using default path of /minecraft.net.aer/configs
     */
	public static void saveSettings(String name, Properties settings) {
        configFile = new File("minecraft.net.aer/configs/" + name + ".config.properties");
        if (!configFile.exists()) {
            if (!createFile(configFile)) {
                return;
            }
        }
        saveSettings(settings, configFile, name);
    }

    /**
     * Loads a set of properties from the given file, using default path of /minecraft.net.aer/configs
     *
     * @param defaults
     * @return The properties that were loaded, or the defaults passed to it if the file couldn't be loaded.
	 */
	public static Properties loadSettings(String name, Properties defaults) {
        configFile = new File("minecraft.net.aer/configs/" + name + ".config.properties");
        if (!configFile.exists()) {
            if (createFile(configFile)) {
                saveSettings(defaults, configFile, name);
            } else {
                if (createDir("minecraft.net.aer/configs/")) {
                    if (createFile(configFile)) {
                        saveSettings(defaults, configFile, name);
                    }
                }
			}
			return defaults;
		}
		Properties props = loadSettings(configFile, defaults);
		return props;
	}


	/**
	 * Saves a set of properties to the given file.
	 *
	 * @return True if the settings saved, false if it failed to save them
	 */
	public static boolean saveSettings(Properties settings, File config, String name) {
		try {
			FileWriter writer = new FileWriter(config);
			settings.store(writer, "Settings for " + name);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Loads a set of properties from the given file.
	 *
	 * @return The properties that were loaded, defaults if it fails to find them
	 */
	public static Properties loadSettings(File config, Properties defaults) {

		Properties props = new Properties(defaults);

		try {

			FileReader reader = new FileReader(config);
			props.load(reader);
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return props;
	}

	/**
	 * Called when the client exits.
	 */
	public static void saveAllSettings() {
		saveSettings("Aer", Aer.settings);
		for (Module m : ModuleManager.moduleList) {
			m.saveSettings();
		}
	}


	/**
	 * Creates a file, used if the config file does not already exist.
	 *
	 * @return True if the file was created, false if it already exists or if it failed to create the file
	 */
	private static boolean createFile(File file) {
		try {
			if (file.createNewFile()) {
				System.out.println(file.getPath() + " File Created in Project root directory");
				return true;
			} else System.out.println("File " + file.getPath() + " already exists in the project root directory");
		} catch (Exception i) {

		}
		return false;
	}

	private static boolean createDir(String path) {
		return new File(path).mkdirs();
	}

	public static Properties loadSettings(String name) {
		return loadSettings(name, new Properties());
	}

}
