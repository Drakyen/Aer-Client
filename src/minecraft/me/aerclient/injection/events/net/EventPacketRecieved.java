package me.aerclient.injection.events.net;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

public class EventPacketRecieved extends EventCancellable {

    public Packet packet;
    public boolean doGui;

    public EventPacketRecieved(Packet p) {
        this.packet = p;
    }

}