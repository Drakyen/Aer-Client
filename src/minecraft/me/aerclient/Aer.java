package me.aerclient;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import me.aerclient.command.CommandManager;
import me.aerclient.injection.events.client.EventGameExit;
import me.aerclient.injection.events.input.EventKeyboardInput;
import me.aerclient.gui.AerGuiMenu;
import me.aerclient.gui.ClickGui;
import me.aerclient.gui.styles.ModernStyle;
import me.aerclient.module.base.Module;
import me.aerclient.module.base.ModuleManager;
import me.aerclient.render.render2D.font.Fonts;
import me.aerclient.config.ConfigHandler;
import me.aerclient.utils.exploits.ClipUtil;
import net.minecraft.client.Minecraft;

import java.util.Properties;

public class Aer {

	public static final String clientName = "Aer Client";
	public static final String clientVersion = "1.0 Beta";
	public static Properties defaults = new Properties();
	public static Properties settings;
	public static ClickGui clickGui;
	public static Aer INSTANCE;

	public Aer() {
		INSTANCE = this;
		defaults.setProperty("CmdPrefix", ".");
		settings = ConfigHandler.loadSettings("Aer", defaults);
		Fonts.load();
		ModuleManager.init();
		CommandManager.init();

		AerGuiMenu AerGuiMenu = new AerGuiMenu();
		clickGui = new ClickGui(new ModernStyle());
		EventManager.register(INSTANCE);
	}

	@EventTarget
	public static void shutdown(EventGameExit event) {
		ConfigHandler.saveAllSettings();
	}

	@EventTarget
	public static void keypress(EventKeyboardInput event) {
		for (Module m : ModuleManager.moduleList) {
			if (m.getKeybind() == event.key) {
				m.toggle();
			}
		}
	}

}
