package net.aer.events;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.entity.Entity;

public class EventPostEntityUpdate implements Event {

	public Entity entity;

	public EventPostEntityUpdate(Entity e) {
		this.entity = e;
	}

}
