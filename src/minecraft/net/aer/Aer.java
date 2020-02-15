package net.aer;

import net.aer.command.CommandManager;
import net.aer.gui.ClickGui;
import net.aer.gui.styles.SciFiStyle;
import net.aer.module.Module;
import net.aer.module.ModuleManager;
import net.aer.render.render2D.Fonts;
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

	public static void load() {

		defaults.setProperty("CmdPrefix", ".");
		settings = ConfigHandler.loadSettings("Aer", defaults);

		Fonts.load();
		ModuleManager.init();
		CommandManager.init();

		clickGui = new ClickGui(new SciFiStyle());

	}

	public static void shutdown() {
		ConfigHandler.saveAllSettings();
		ModuleManager.onExit();
	}

	public static void keypress(int key) {
		for (Module m : ModuleManager.moduleList) {
			if (m.getKeybind() == key) {
				m.toggle();
			}
		}
	}

}
