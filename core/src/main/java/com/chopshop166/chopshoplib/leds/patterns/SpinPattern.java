package com.chopshop166.chopshoplib.leds.patterns;

import com.chopshop166.chopshoplib.leds.AnimatedPattern;
import com.chopshop166.chopshoplib.leds.SegmentBuffer;
import edu.wpi.first.wpilibj.util.Color;

/** Display a spinning dot. */
public class SpinPattern extends AnimatedPattern {
    /** The position of the indicator. */
    private int ledPosition;

    /** Constructor. */
    public SpinPattern() {
        super(0.05);
    }

    @Override
    public void initialize(final SegmentBuffer buffer) {
        super.initialize(buffer);
        this.ledPosition = 0;
    }

    @Override
    public void animate(final SegmentBuffer buffer) {
        this.ledPosition++;
        if (this.ledPosition >= buffer.getLength()) {
            this.ledPosition = 0;
        }
        buffer.setAll(Color.kBlack);
        buffer.set(this.ledPosition, Color.kGreen);
    }

    @Override
    public String toString() {
        return "SpinPattern()";
    }
}
