package net.aer.utils.threads;

public class ValueFadeThread implements Runnable {

    private ValueType value;
    private Object start;
    private Object end;
    private ValueFadeable out;
    private int time;

    public ValueFadeThread(Object start, Object end, int time, ValueType type, ValueFadeable output) {
        this.start = start;
        this.end = end;
        this.value = type;
        this.out = output;
        this.time = time;
    }


    public void run() {
        int intstart;
        int intend;
        int intdif;
        long longstart;
        long longend;
        long longdif;
        float floatstart;
        float floatend;
        float floatdif;
        double doublestart;
        double doubleend;
        double doubledif;

        if (value == ValueType.INT) {
            intstart = (int) start;
            intend = (int) end;
            intdif = ((int) end - (int) start) / time;
            while (time > 0) {
                time--;
                out.intout = intstart;
                intstart += intdif;
            }
        }

        if (value == ValueType.LONG) {
            longstart = (long) start;
            longend = (long) end;
            longdif = ((long) end - (long) start) / time;
            while (time > 0) {
                time--;
                out.longout = longstart;
                longstart += longdif;
            }
        }

        if (value == ValueType.FLOAT) {
            floatstart = (float) start;
            floatend = (float) end;
            floatdif = ((float) end - (float) start) / time;
            while (time > 0) {
                time--;
                out.floatout = floatstart;
                floatstart += floatdif;
            }
        }

        if (value == ValueType.DOUBLE) {
            doublestart = (double) start;
            doubleend = (double) end;
            doubledif = ((double) end - (double) start) / time;
            while (time > 0) {
                time--;
                out.doubleout = doublestart;
                doublestart += doubledif;
            }
        }

    }

}
