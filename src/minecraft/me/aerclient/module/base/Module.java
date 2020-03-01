package me.aerclient.module.base;

import com.darkmagician6.eventapi.EventManager;
import me.aerclient.Aer;
import me.aerclient.config.ConfigHandler;
import me.aerclient.config.valuesystem.Value;
import me.aerclient.config.valuesystem.ValueManager;
import me.aerclient.utils.Utilities;
import net.minecraft.client.Minecraft;

import java.util.Properties;

public class Module implements Utilities {

	//Properties objects used to save module settings.
	protected Properties defaults = new Properties();
	public Properties settings = new Properties();

	//Basic module variables
	private String name = "NO_NAME";
	private String description = "NO_DESCRIPTION";
	private Category category;
	private int keybind = 0;
	protected boolean active = false;



	/**
	 * Reduced constructor for Modules
	 */
	public Module(String name, Category cat) {
		this(name, cat, "No description available", 0);
	}

	/**
	 * Reduced constructor for Modules
	 */
	public Module(String name, Category cat, int keybind) {
		this(name, cat, "No description available", keybind);
	}

	/**
	 * Reduced constructor for Modules
	 */
	public Module(String name, Category cat, String descrpt) {
		this(name, cat, descrpt, 0);
	}

	/**
	 * Constructor for Modules
	 */
	public Module(String name, Category cat, String descrpt, int keybind) {
		this.name = name;
		this.description = descrpt;
		this.category = cat;
		this.keybind = keybind;
		this.defaults.setProperty("Active", "false");
		this.defaults.setProperty("Keybind", "" + this.keybind);
		loadSettings();
		if (this.active) {
			EventManager.register(this);
		}
	}


	/**
	 * Used to initialise anything that needs initialising.
	 */
	public void setup() {

	}

	/**
	 * Called to switch the module state from on to off or vice versa, also calls onEnable/onDisable for the module.
	 */
	public void toggle() {
		this.setActiveState(!isActive());
		if (this.isActive() == true) {
			EventManager.register(this);
			this.onEnable();
		} else if (this.isActive() == false) {
			this.onDisable();
			EventManager.unregister(this);
		}
	}

	/**
	 * Called once when this module is enabled
	 */
	public void onEnable() {

	}

	/**
	 * Called once when this module is disabled
	 */
	public void onDisable() {

	}


	/**
	 * Directly sets this module to on or off
	 */
	public void setActiveState(boolean activeState) {
		this.active = activeState;
		this.settings.put("Active", "" + activeState);
	}

	/**
	 * @return Whether the module is on or off.
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * Loads the settings for this module.
	 */
	public void loadSettings() {
		this.settings = ConfigHandler.loadSettings(this.name, this.defaults);
		this.active = Boolean.parseBoolean(this.settings.getProperty("Active"));
		this.keybind = Integer.parseInt(this.settings.getProperty("Keybind"));
	}

	/**
	 * Saves the settings for this module.
	 */
	public void saveSettings() {
		for (Value v : ValueManager.getAllValuesFrom(this.getName())) {
			v.addToProperties(this.settings);
		}
		ConfigHandler.saveSettings(this.name, this.settings);
	}

	/**
	 * Sets the keybind for this module.
	 */
	public void setKeybind(int keybind) {
		this.keybind = keybind;
		this.settings.put("Keybind", "" + keybind);
	}

	/**
	 * @return The keybind of this module
	 */
	public int getKeybind() {
		return this.keybind;
	}

	/**
	 * @return The name of this module
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return The category of this module
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @return The description of this module
	 */
	public String getDescription() {
		return description;
	}



}
