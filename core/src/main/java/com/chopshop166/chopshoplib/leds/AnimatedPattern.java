package com.chopshop166.chopshoplib.leds;

import edu.wpi.first.wpilibj.Timer;

/** A pattern that animates on a set interval. */
public abstract class AnimatedPattern extends Pattern {

    /** The timer to trigger the animation. */
    private final Timer timer = new Timer();
    /** The time interval (in seconds) to update on. */
    private final double delay;

    /**
     * Constructor.
     * 
     * @param delay The interval to update on (in seconds).
     */
    public AnimatedPattern(final double delay) {
        super();
        this.delay = delay;
    }

    /**
     * Show the next stage of the animation.
     * 
     * @param buffer The buffer to update.
     */
    public abstract void animate(SegmentBuffer buffer);

    @Override
    public void initialize(final SegmentBuffer buffer) {
        this.timer.restart();
    }

    @Override
    public void update(final SegmentBuffer buffer) {
        if (this.timer.advanceIfElapsed(this.delay)) {
            this.animate(buffer);
        }
    }
}
