package me.aerclient.injection.events.render;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.client.gui.GuiScreen;

public class EventGuiClosed implements Event {

	public GuiScreen gui;

	public EventGuiClosed(GuiScreen g) {
		this.gui = g;
	}


}
