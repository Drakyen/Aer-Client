package net.aer;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.aer.command.CommandManager;
import net.aer.events.input.EventKeyboardInput;
import net.aer.gui.AerGuiMenu;
import net.aer.gui.ClickGui;
import net.aer.gui.styles.ModernStyle;
import net.aer.module.base.Module;
import net.aer.module.base.ModuleManager;
import net.aer.render.render2D.font.Fonts;
import net.aer.utils.config.ConfigHandler;
import net.aer.utils.exploits.ClipUtil;
import net.minecraft.client.Minecraft;

import java.util.Properties;

public class Aer {

	public static final String clientName = "Aer Client";
	public static final String clientVersion = "1.0 Beta";
	public static Properties defaults = new Properties();
	public static Properties settings;
	public static final Minecraft minecraft = Minecraft.getMinecraft();
	public static final ClipUtil clipUtil = new ClipUtil();
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

	public static void shutdown() {
		ConfigHandler.saveAllSettings();
		ModuleManager.onExit();
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
