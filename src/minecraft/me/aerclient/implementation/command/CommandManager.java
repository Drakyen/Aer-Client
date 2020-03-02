package me.aerclient.implementation.command;

import me.aerclient.implementation.command.commands.*;
import me.aerclient.implementation.module.base.Module;
import me.aerclient.implementation.module.base.ModuleManager;
import me.aerclient.visual.render.render2D.ChatColour;
import me.aerclient.visual.render.render2D.ChatUtils;

import java.util.ArrayList;


/**
 * Initializes commands and allows you to call commands by string
 */
public class CommandManager {

	private static ArrayList<Command> commands = new ArrayList<>();


	/**
	 * Initilizes this command manager by adding all the commands to a array
	 */
	public static void init() {
		addCommand(new SetKeyBind());
		addCommand(new CMDPrefix());
		addCommand(new Help());
		addCommand(new XRayBlocks());
		addCommand(new Clip());
		addCommand(new Vanish());
	}


	/**
	 * Adds a command to the list.
	 */
	public static void addCommand(Command c) {
		commands.add(c);
	}


	/**
	 * Returns a arrayList of all commands loaded in this CommandManager
	 */
	public static ArrayList<Command> getCommands() {
		return commands;
	}

	/**
	 * Gets a command object by alias - the name the command is called by, not the class name - returns null if the command isn't found.
	 *
	 * @param name The name of the command to be found
	 * @return The command found, or null if the command wasn't found.
	 */
	public static Object getCommandByName(String name) {
		for (Command c : getCommands()) {
			if (c.getAlias().equalsIgnoreCase(name)) {
				return c;
			}
		}
		return null;
	}


	/**
	 * Calls any command that matches the input string, returns a exception if the syntax is wrong or the command is invalid
	 */
	public static void callCommand(String input) {
		String[] split = input.split(" ");
		String command = split[0];
		String args = input.substring(command.length()).trim();
		for (Command c : getCommands()) {
			if (c.getAlias().equalsIgnoreCase(command)) {
				try {
					c.onCommand(command, args.split(" "));
				} catch (Exception e) {
					ChatUtils.clearChat(false, "CMD");
					ChatUtils.sendChatMessage("Invalid Command Usage!", ChatColour.red, "CMD", true);
					ChatUtils.sendChatMessage(c.getSyntax(), ChatColour.red, "CMD", false);
				}
				return;
			}
		}
		for(Module m : ModuleManager.visibleModuleList){
			if(command.equalsIgnoreCase(m.getName())){
				ValueCommand.call(m, args.split(" "));
				return;
			}
		}
		ChatUtils.sendChatMessage("Command not found! [Try Help?]", ChatColour.red, "CMD", true);
	}

}
