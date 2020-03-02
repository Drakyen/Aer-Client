package me.aerclient.implementation.command.commands;

import me.aerclient.implementation.command.Command;
import me.aerclient.visual.render.render2D.ChatColour;
import me.aerclient.visual.render.render2D.ChatUtils;

public class Clip extends Command {


    public Clip() {
        super("Clip", "Allows you to phase through blocks", "Clip [H/V] [DISTANCE]");
    }


    @Override
    public void onCommand(String command, String[] args) throws Exception {


        int distanceV = 0;
        int distanceH = 0;

        if (args[0].equalsIgnoreCase("V")) {
            distanceV = Integer.parseInt(args[1]);
            clipUtil.Clip(distanceH, distanceV, true, false);

        } else if (args[0].equalsIgnoreCase("H")) {
            distanceH = Integer.parseInt(args[1]);
            clipUtil.Clip(distanceH, distanceV, true, false);
        } else if (args[0].isEmpty()) {
            ChatUtils.sendChatMessage(this.getSyntax(), ChatColour.lightPurple, "CMD", true);
        } else {
            throw new Exception();
        }


    }
}