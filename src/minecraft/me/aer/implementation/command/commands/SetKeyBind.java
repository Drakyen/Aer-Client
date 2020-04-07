package me.aer.implementation.command.commands;

import me.aer.implementation.command.Command;
import me.aer.implementation.module.base.Module;
import me.aer.implementation.module.base.ModuleManager;
import me.aer.visual.render.ChatColour;
import me.aer.visual.render.ChatUtils;
import org.lwjgl.input.Keyboard;


/**
 * Allows you to set or remove the keybind for modules
 */
public class SetKeyBind extends Command {


	/**
	 * Constructor for SetKeyBind
	 */
	public SetKeyBind() {
		super("Bind", "Sets and removes keybinds", "bind [MOD] [KEY] | bind remove [MOD/ALL]");
	}


	@Override
	public void onCommand(String command, String[] args) throws Exception {
		boolean all = false;
		boolean thr0w = true;
		if (args[0].equalsIgnoreCase("remove")) {
			for (Module m : ModuleManager.moduleList) {
				if (args[1].equalsIgnoreCase(m.getName())) {
					m.setKeybind(0);
					ChatUtils.sendChatMessage(m.getName() + " has been unbound", ChatColour.lightPurple, "CMD", true);
					thr0w = false;
				} else if (args[1].equalsIgnoreCase("all")) {
					m.setKeybind(0);
					all = true;
				}
				if (all) {
					ChatUtils.sendChatMessage("All keys cleared", ChatColour.lightPurple, "CMD", true);
					thr0w = false;
				}
			}
		} else if (args[0].isEmpty()) {
			ChatUtils.sendChatMessage(this.description, ChatColour.lightPurple, "CMD", true);
			thr0w = false;
		} else {
			for (Module m : ModuleManager.moduleList) {
				if (args[0].equalsIgnoreCase(m.getName())) {
					args[1] = args[1].toUpperCase();
					int key = Keyboard.getKeyIndex(args[1]);
					m.setKeybind(Keyboard.getKeyIndex(Keyboard.getKeyName(key)));
					ChatUtils.sendChatMessage(m.getName() + " has been bound to " + args[1], ChatColour.lightPurple, "CMD", true);
					thr0w = false;
				}
			}
		}
		if (thr0w) {
			throw new Exception();
		}
	}
}
