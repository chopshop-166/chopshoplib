package com.chopshop166.chopshoplib.leds;

/** The base pattern object for LED patterns. */
public abstract class Pattern {

    /**
     * The function to run when the pattern starts running.
     * 
     * @param buffer The buffer to operate on.
     */
    public abstract void initialize(SegmentBuffer buffer);

    /**
     * The function to run each time the LED scheduler runs.
     * 
     * @param buffer The buffer to operate on.
     */
    public void update(final SegmentBuffer buffer) {
        // By default patterns are static
    }
}
