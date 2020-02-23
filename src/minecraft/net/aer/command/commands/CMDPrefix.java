package net.aer.command.commands;

import net.aer.Aer;
import net.aer.command.Command;
import net.aer.render.render2D.ChatColour;
import net.aer.render.render2D.ChatUtils;
import javax.annotation.Nonnull;

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
