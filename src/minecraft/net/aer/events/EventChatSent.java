package net.aer.events;


import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventChatSent extends EventCancellable {

	public String text;

	public EventChatSent(String text) {
		this.text = text;
	}

}
