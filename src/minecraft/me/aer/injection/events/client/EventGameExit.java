package me.aer.injection.events.client;

import com.darkmagician6.eventapi.events.Event;

public class EventGameExit implements Event {

    private boolean crashed;

    public EventGameExit(boolean crash) {
        crashed = crash;
    }

    public boolean didCrash(){
        return crashed;
    }

}
