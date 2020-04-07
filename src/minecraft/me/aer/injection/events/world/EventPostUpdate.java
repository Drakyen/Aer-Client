package me.aer.injection.events.world;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.entity.Entity;

public class EventPostUpdate implements Event {

	public Entity entity;

	public EventPostUpdate(Entity e) {
		this.entity = e;
	}

}
