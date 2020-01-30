package net.aer.events;

import com.darkmagician6.eventapi.events.Event;

public class EventKeyboardInput implements Event {

	public int key;

	public EventKeyboardInput(int key) {
		this.key = key;
	}
}
