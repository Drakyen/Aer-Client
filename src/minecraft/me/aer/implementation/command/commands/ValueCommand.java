package me.aer.implementation.command.commands;

import me.aer.config.valuesystem.*;
import me.aer.implementation.module.base.Module;
import me.aer.visual.render.ChatColour;
import me.aer.visual.render.ChatUtils;

public class ValueCommand {

    public static void call(Module m, String[] args) {
        Value v  = ValueManager.get(m.getName(), args[0]);
        if(v == null){
            ChatUtils.sendChatMessage("Invalid property! \u00A77[" + m.getName() + " does not contain property '" + args[0] + "']", ChatColour.red, true);
            return;
        }
        if(args.length < 2){
            ChatUtils.sendChatMessage("Cannot set " + m.getName() + " " + v.getName().toLowerCase() + " to nothing!", ChatColour.red, true);
            return;
        }
        String input = args[1];
        if(v instanceof NumberValue){
            if(v.getDefault() instanceof Integer){
                try {
                    v.setObject(Integer.parseInt(input));
                } catch(NumberFormatException e ) {
                    ChatUtils.sendChatMessage("Invalid number!", ChatColour.red, true);
                    return;
                }
            } else
            if(v.getDefault() instanceof Double){
                try {
                    v.setObject(Double.parseDouble(input));
                } catch(NumberFormatException e ) {
                    ChatUtils.sendChatMessage("Invalid number!", ChatColour.red, true);
                    return;
                }
            } else
            if(v.getDefault() instanceof Float){
                try {
                    v.setObject(Float.parseFloat(input));
                } catch(NumberFormatException e ) {
                    ChatUtils.sendChatMessage("Invalid number!", ChatColour.red, true);
                    return;
                }
            } else
            if(v.getDefault() instanceof Long){
                try {
                    v.setObject(Long.parseLong(input));
                } catch(NumberFormatException e ) {
                    ChatUtils.sendChatMessage("Invalid number!", ChatColour.red, true);
                    return;
                }
            }
            ChatUtils.sendChatMessage(m.getName() + " " + v.getName().toLowerCase() + " set to " + input, ChatColour.gray, true);
        }
        else if(v instanceof BooleanValue){
            try {
                v.setObject(Boolean.parseBoolean(input));
            } catch(Exception e ) {
                ChatUtils.sendChatMessage("Invalid boolean!", ChatColour.red, true);
                return;
            }
            ChatUtils.sendChatMessage(m.getName() + " " + v.getName().toLowerCase() + " set to " + input, ChatColour.gray, true);
        }
        else if(v instanceof ModeValue){
            for(String s : ((ModeValue) v).getModes()){
                if(input == s){
                    try {
                        v.setObject(input);
                        ChatUtils.sendChatMessage(m.getName() + " " + v.getName().toLowerCase() + " set to " + input, ChatColour.gray, true);
                        return;
                    } catch(Exception e ) {
                        ChatUtils.sendChatMessage("Invalid mode!", ChatColour.red, true);
                        return;
                    }
                }
            }
            ChatUtils.sendChatMessage("Invalid mode!", ChatColour.red, true);
        }


    }
}
