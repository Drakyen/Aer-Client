package me.aerclient.injection.events.client;

import com.darkmagician6.eventapi.events.Event;
import me.aerclient.config.valuesystem.Value;

public class EventValueChanged implements Event {

	public Value value;

	public EventValueChanged(Value v) {
		this.value = v;
	}

}
