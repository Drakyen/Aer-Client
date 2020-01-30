package net.aer.events;

import com.darkmagician6.eventapi.events.Event;
import net.aer.utils.valuesystem.Value;

public class EventValueChanged implements Event {

	public Value value;

	public EventValueChanged(Value v) {
		this.value = v;
	}

}
