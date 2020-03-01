package me.aerclient.injection.events.net;


import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventChatSent extends EventCancellable {

	public String text;

	public EventChatSent(String text) {
		this.text = text;
	}

}
