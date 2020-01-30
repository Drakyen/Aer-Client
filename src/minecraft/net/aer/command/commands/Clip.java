package net.aer.command.commands;

import net.aer.Aer;
import net.aer.command.Command;
import net.aer.render.render2D.ChatColour;
import net.aer.render.render2D.ChatUtils;

public class Clip extends Command {


    public Clip() {
        super("Clip", "Allows you to phase through blocks", "Clip [H/V] [DISTANCE]");
    }


    @Override
    public void onCommand(String command, String[] args) throws Exception {


        int distanceV = 0;
        int distanceH = 0;

        if (args[0].equalsIgnoreCase("V")) {
            distanceV = Integer.valueOf(args[1]);
            Aer.clipUtil.Clip(distanceH, distanceV, true, false);

        } else if (args[0].equalsIgnoreCase("H")) {
            distanceH = Integer.valueOf(args[1]);
            Aer.clipUtil.Clip(distanceH, distanceV, true, false);
        } else if (args[0].isEmpty()) {
            ChatUtils.sendChatMessage(this.getSyntax(), ChatColour.lightPurple, "CMD", true);
        } else {
            throw new Exception();
        }


    }
}