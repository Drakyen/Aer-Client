package net.aer.events;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.util.DamageSource;

public class EventEntityAttacked extends EventCancellable {

	public DamageSource source;
	public float amount;

	public EventEntityAttacked(DamageSource source, float amount) {
		this.source = source;
		this.amount = amount;
		EventManager.call(this);
	}

}
