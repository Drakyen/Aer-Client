package me.aer;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import me.aer.config.ConfigHandler;
import me.aer.implementation.command.CommandManager;
import me.aer.implementation.module.base.Module;
import me.aer.implementation.module.base.ModuleManager;
import me.aer.injection.events.client.EventGameExit;
import me.aer.injection.events.input.EventKeyboardInput;
import me.aer.visual.gui.click.ClickGuiUI;
import me.aer.visual.gui.screen.AerGuiMenu;
import me.aer.visual.render.Fonts;
import me.aer.visual.style.modern.ModernStyle;
import org.lwjgl.input.Keyboard;

import java.util.Properties;

public class Aer {

	public static final String clientName = "Aer Client";
	public static final String clientVersion = "1.0 Beta";
	public static Properties defaults = new Properties();
	public static Properties settings;
	public static ClickGuiUI clickGui;
	public static Aer INSTANCE;

	public Aer() {
		INSTANCE = this;
		defaults.setProperty("CmdPrefix", ".");
		settings = ConfigHandler.loadSettings("Aer", defaults);
		Fonts.load();
		ModuleManager.init();
		CommandManager.init();

		AerGuiMenu AerGuiMenu = new AerGuiMenu();
		clickGui = new ClickGuiUI(new ModernStyle("ModernStyle"));
		EventManager.register(INSTANCE);
	}

	@EventTarget
	public static void shutdown(EventGameExit event) {
		ConfigHandler.saveAllSettings();
	}

	@EventTarget
	public static void keypress(EventKeyboardInput event) {
		if (Keyboard.getEventKeyState()) {
			int key = Keyboard.getEventKey();
			for (Module m : ModuleManager.moduleList) {
				if (m.getKeybind() == key) {
					m.toggle();
				}
			}
		}
	}

}
