package me.aerclient.utils.threads;

public class ValueFadeThread implements Runnable {

    private double start;
    private double end;
    public double out;
    private int time;

    public ValueFadeThread(double start, double end, int time) {
        this.start = start;
        this.end = end;
        this.time = time;
    }


    public void run() {
        Number dif = (end - start) / time;
        while (time > 0) {
            time--;
            out = start;
            start += dif.doubleValue();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
