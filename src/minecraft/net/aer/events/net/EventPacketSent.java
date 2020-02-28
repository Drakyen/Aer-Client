package net.aer.events.net;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

public class EventPacketSent extends EventCancellable {

    public Packet packet;

    public EventPacketSent(Packet p) {
        this.packet = p;
    }

}