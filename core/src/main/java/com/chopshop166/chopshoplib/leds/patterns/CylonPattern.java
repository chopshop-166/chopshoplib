package com.chopshop166.chopshoplib.leds.patterns;

import com.chopshop166.chopshoplib.leds.AnimatedPattern;
import com.chopshop166.chopshoplib.leds.SegmentBuffer;
import edu.wpi.first.wpilibj.util.Color;

/** Display a dot that bounces from side to side. */
public class CylonPattern extends AnimatedPattern {
    /** The color of the dot. */
    private final Color color;
    /** The position of the indicator. */
    private int ledPosition;
    /** Whether the dot is moving backwards. */
    private boolean isReversed;

    /** Constructor. */
    public CylonPattern() {
        this(Color.kRed);
    }

    /**
     * Constructor.
     * 
     * @param color The color of the dot.
     */
    public CylonPattern(final Color color) {
        super(0.05);
        this.color = color;
    }

    @Override
    public void initialize(final SegmentBuffer buffer) {
        super.initialize(buffer);
        this.ledPosition = 0;
        this.isReversed = false;
    }

    @Override
    public void animate(final SegmentBuffer buffer) {
        if (this.isReversed) {
            this.ledPosition--;
        } else {
            this.ledPosition++;
        }
        if (this.ledPosition >= buffer.getLength()) {
            this.isReversed = true;
        } else if (this.ledPosition <= 0) {
            this.isReversed = false;
        }
        buffer.setAll(Color.kBlack);
        buffer.set(this.ledPosition, this.color);
    }

    @Override
    public String toString() {
        return String.format("CylonPattern(%s)", this.color.toString());
    }
}
