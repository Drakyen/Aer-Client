package me.aer.injection.events.net;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventChatRecieved extends EventCancellable {

	public String text;

	public EventChatRecieved(String text) {
		this.text = text;
	}

}
