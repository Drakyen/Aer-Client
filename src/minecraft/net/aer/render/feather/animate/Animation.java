package net.aer.render.feather.animate;

/**
 * Created by KNOXDEV on 6/10/2016.
 * A tick-based transition object.
 */
public class Animation {
    private Transition transition = Transition.LINEAR;
    protected int duration = 20;
    private boolean isForward = true;
    private boolean inProgress = false;
    protected double progress = 0;

    public Animation start() {
        this.inProgress = true;
        return this;
    }

    public Animation stop() {
        this.inProgress = false;
        return this;
    }

    public Animation cancel() {
        return reset().stop();
    }

    public Animation reset() {
        this.progress = isForward?0:1;
        return this;
    }

    public Animation reverse() {
        this.isForward = false;
        return start();
    }

    public Animation forward() {
        this.isForward = true;
        return start();
    }

    public Animation setTransition(Transition transition) {
        this.transition = transition;
        return this;
    }

    public Animation setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public boolean isRunning() {
        return this.inProgress;
    }

    public double get() {
        increment(1);
        return translate(progress);
    }

    public double get(double low, double high) {
        return ((high - low)* get() + low);
    }

    protected void increment(int ticks) {
        if(!inProgress) return;
        if(!isForward) ticks*= -1;
        this.progress += (double)ticks/(double)duration;

        if(progress > 1) {
            this.progress = 1;
            this.inProgress = false;
        } else if (progress < 0) {
            this.progress = 0;
            this.inProgress = false;
        }
    }

    protected double translate(double progress) {
        switch(this.transition) {
            case CURVE:
                return progress*progress;
            case STEEP_CURVE:
                return Math.pow(progress, 3);
            case BEZIER_CURVE:
                return Math.pow(-1+Math.sqrt(-progress+1), 2);
            case INVERSE_CURVE:
                return -Math.pow(progress-1, 2) + 1;
            case INVERSE_STEEP_CURVE:
                return Math.pow(progress-1, 3) + 1;
            case RUBBER:
                final double trans = -Math.sin(10.0*progress)/(10.0*progress) + 1;
                return trans>0?trans:0;
            case INERTIA:
                return Math.cbrt(progress-0.5)/Transition.INERTIA_CONST + 0.5;
            case INSTANT:
                return Math.round(progress);
        }
        return progress;
    }

    public enum Transition {
        LINEAR,
        CURVE,
        STEEP_CURVE,
        BEZIER_CURVE,
        INVERSE_CURVE,
        INVERSE_STEEP_CURVE,
        RUBBER,
        INERTIA,
        INSTANT;

        final static double INERTIA_CONST = Math.cbrt(0.5)/0.5;
    }
}
