package me.aer.implementation.command.commands;

import me.aer.Aer;
import me.aer.implementation.command.Command;
import me.aer.visual.render.ChatColour;
import me.aer.visual.render.ChatUtils;

/**
 * A command to change the prefix for commands
 */
public class CMDPrefix extends Command {

    /**
     * Constructor for CMDPrefix
     */
    public CMDPrefix() {
        super("CmdPrefox", "Sets the command prefix", "CmdPrefix [SET] [KEY]");
    }

    public void onCommand(String command, String[] args) throws Exception {
        if (args[0].equalsIgnoreCase("set")) {
            if (args[1].isEmpty()) {
                ChatUtils.sendChatMessage("Invalid prefix. Prefix has not been changed.", ChatColour.red, "CMD", true);
            } else {
                Aer.settings.setProperty("CmdPrefix", args[1]);
                ChatUtils.sendChatMessage("Prefix set! [" + args[1] + "]", ChatColour.lightPurple, "CMD", true);
            }

        } else if (args[0].isEmpty()) {
            ChatUtils.sendChatMessage(description, ChatColour.lightPurple, "CMD", true);
        } else {
            throw new Exception();
        }
    }


}
