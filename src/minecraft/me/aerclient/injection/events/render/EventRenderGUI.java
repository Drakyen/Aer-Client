package me.aerclient.injection.events.render;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.client.gui.GuiScreen;

public class EventRenderGUI implements Event {

    public GuiScreen gui;

    public EventRenderGUI(GuiScreen guiIn){
        gui = guiIn;
    }


}
