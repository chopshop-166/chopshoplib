package com.chopshop166.chopshoplib.leds.patterns;

import com.chopshop166.chopshoplib.leds.AnimatedPattern;
import com.chopshop166.chopshoplib.leds.SegmentBuffer;

import edu.wpi.first.wpilibj.util.Color;

/** Pattern to flash on regular intervals. */
public class FlashPattern extends AnimatedPattern {
    /** The color to flash. */
    private final Color color;
    /** Whether the light is on or not. */
    private boolean isOn;

    /**
     * Constructor.
     * 
     * @param color The color to flash.
     * @param delay The interval to flash on.
     */
    public FlashPattern(final Color color, final double delay) {
        // If super is called with a delay, then the pattern scheduler will call
        // update() after that many seconds have elapsed
        // Otherwise it runs update every frame
        super(delay);
        this.color = color;
    }

    @Override
    public void initialize(final SegmentBuffer buffer) {
        super.initialize(buffer);
        this.isOn = true;
    }

    @Override
    public void animate(final SegmentBuffer segment) {
        this.isOn = !this.isOn;
        segment.setAll(this.isOn ? this.color : Color.kBlack);
    }

    @Override
    public String toString() {
        return String.format("FlashPattern(%s)", this.color.toString());
    }
}
