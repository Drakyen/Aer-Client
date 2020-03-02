package me.aerclient.visual.render.feather.animate;

/**
 * Created by KNOXDEV on 6/10/2016.
 * Another type of transition object that uses Time instead of the default ticks.
 */
public class TimeAnimation extends Animation {
    private long prevTime;

    public TimeAnimation() {
        this.duration = 400;
    }

    @Override
    public Animation start() {
        prevTime = System.currentTimeMillis();
        return super.start();
    }

    @Override
    public double get() {
        final long time = System.currentTimeMillis();
        increment((int)(time - prevTime));
        this.prevTime = time;
        return translate(progress);
    }
}
