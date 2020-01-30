package net.aer.events;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.entity.Entity;

public class EventPreUpdate implements Event {

	public Entity entity;

	public EventPreUpdate(Entity e) {
		this.entity = e;
	}

}
