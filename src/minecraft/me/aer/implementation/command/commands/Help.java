package me.aer.implementation.command.commands;

import me.aer.implementation.command.Command;
import me.aer.implementation.command.CommandManager;
import me.aer.implementation.module.base.Module;
import me.aer.implementation.module.base.ModuleManager;
import me.aer.visual.render.ChatColour;
import me.aer.visual.render.ChatUtils;


/**
 * Returns various helpful information
 */
public class Help extends Command {

	/**
	 * Constructor for Help
	 */
	public Help() {
		super("Help", "Displays useful information", "Help [LIST/MODULE] [COMMANDS/MODULES]");
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if (args[0].equalsIgnoreCase("list") && args[1].equalsIgnoreCase("modules")) {
			ChatUtils.sendChatMessage("Modules: ", ChatColour.lightPurple, "CMD", true);
			for (Module m : ModuleManager.visibleModuleList) {
				ChatUtils.sendChatMessage(m.getName(), ChatColour.gray, "CMD", false);
			}
		} else if (args[0].equalsIgnoreCase("list") && args[1].equalsIgnoreCase("commands")) {
			ChatUtils.sendChatMessage("Commands: ", ChatColour.lightPurple, "CMD", true);
			for (Command c : CommandManager.getCommands()) {
				ChatUtils.sendChatMessage(c.getAlias(), ChatColour.gray, "CMD", false);
			}
		} else if (!args[0].isEmpty()) {
			boolean notFound = true;
			for (Module m : ModuleManager.visibleModuleList) {
				if (args[0].equalsIgnoreCase(m.getName())) {
					ChatUtils.sendChatMessage("Module description [" + m.getName() + "]:", ChatColour.lightPurple, "CMD", true);
					ChatUtils.sendChatMessage(m.getDescription(), ChatColour.gray, true);
					notFound = false;
				}
			}
			if (notFound) {
				ChatUtils.sendChatMessage("Couldn't find module", ChatColour.lightPurple, "CMD", true);
			}

		} else {
			ChatUtils.sendChatMessage(this.getSyntax(), ChatColour.lightPurple, "CMD", true);
		}
	}

}
