package net.aer.utils.world;

import net.minecraft.client.Minecraft;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TimerUtil {

    /**
     * HashMap to track the time left until each object's method should be called
     */
    private static HashMap<Object, Integer> timers = new HashMap<Object, Integer>();

    /**
     * HashMap that tracks what method in each object should be called
     */
    private static HashMap<Object, Method> methods = new HashMap<Object, Method>();

    /**
     * Elapsed time since the timer was last updated, in milliseconds
     */
    private static long elapsedTime = 0;

    /**
     * The system time as of last sync, in milliseconds
     */
    private static long lastSyncedTime = 0;

    /**
     * Keeps track of whether to update method call timers, since it only updates them every tenth of a second rather than every millisecond
     */
    private static long incrementTime = 0;

    /**
     * The current relative time in milliseconds, rolls back to zero each second.
     */
    private static long time = 0;


    /**
     * Increments all timers and checks whether any of the registered methods need calling yet
     */
    private static void increment() {
        for (Map.Entry entry : timers.entrySet()) {
            int i = (Integer) entry.getValue();
            i--;
            if (i == 0) {
                try {
                    Object key = entry.getKey();
                    Method meth = methods.get(key);
                    methods.remove(key);
                    timers.remove(key);
                    meth.invoke(key, null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                entry.setValue(i);
            }
        }
    }

    /**
     * Calls the given method on the given object after the given time.
     *
     * @param time   Time until the method should be called, in tenths of a second (10 = 1 second delay)
     * @param obj    The owner of the method, should usually be the object calling this function
     * @param method The method to be invoked after the given time, must return void otherwise this function will do nothing.
     */
    public static void callAfter(int time, Object obj, Method method) {
        if (method.getReturnType() == Void.TYPE) {
            timers.put(obj, time);
            methods.put(obj, method);
        }
    }

    /**
     * Updates all fields with the system time
     */
    public static void update() {
        long i = Minecraft.getSystemTime();
        elapsedTime = i - lastSyncedTime;
        lastSyncedTime = i;
        time += elapsedTime;
        incrementTime -= elapsedTime;
        if (time >= 1000) {
            time = 0;
        }
        if (incrementTime <= 0) {
            increment();
            incrementTime = 100;
        }

    }


    public static class Timer {

        /**
         * A timer, as a subclass of TimerUtil
         */
        public Timer() {
            this.prevMS = 0L;
        }

        /**
         * The system time when this was last reset
         */
        private long prevMS;

        /**
         * Checks whether the given amount of time has passed since this timer was reset
         *
         * @param milliSec
         * @return Whether the given amount of time has passed
         */
        public boolean delay(float milliSec) {
            return (float) (getTime() - this.prevMS) >= milliSec;
        }

        /**
         * Resets the delay timer
         */
        public void reset() {
            this.prevMS = getTime();
        }

        /**
         * @return The system time in milliseconds
         */
        public long getTime() {
            return System.nanoTime() / 1000000L;
        }

        /**
         * @return The difference between the last reset time and the current time
         */
        public long getDifference() {
            return this.getTime() - this.prevMS;
        }

        /**
         * Sets the difference.. not sure what this is needed for.
         *
         * @param difference
         */
        public void setDifference(long difference) {
            this.prevMS = (getTime() - difference);
        }

    }

}
