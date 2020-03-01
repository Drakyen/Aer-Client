package me.aerclient.injection.events.entity;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.entity.Entity;

public class EventPreEntityUpdate implements Event {

	public Entity entity;

	public EventPreEntityUpdate(Entity e) {
		this.entity = e;
	}

}
