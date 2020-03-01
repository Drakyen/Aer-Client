package me.aerclient.injection.events.render;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.client.gui.GuiScreen;

public class EventGuiOpened extends EventCancellable {

	public GuiScreen gui;

	public EventGuiOpened(GuiScreen g) {
		this.gui = g;
	}


}
