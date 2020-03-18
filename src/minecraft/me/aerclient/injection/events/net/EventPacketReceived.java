package me.aerclient.injection.events.net;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

public class EventPacketReceived extends EventCancellable {

    public Packet packet;

    public EventPacketReceived(Packet p) {
        this.packet = p;
    }

}