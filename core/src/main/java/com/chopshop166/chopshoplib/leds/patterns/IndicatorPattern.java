package com.chopshop166.chopshoplib.leds.patterns;

import java.util.function.BooleanSupplier;

import com.chopshop166.chopshoplib.leds.Pattern;
import com.chopshop166.chopshoplib.leds.SegmentBuffer;

import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.util.Color;

/** Show one color when a state is true, and another when the state is false. */
public class IndicatorPattern extends Pattern {
    /** The color when the indicator is true. */
    private final Color trueColor;
    /** The color when the indicator is false. */
    private final Color falseColor;
    /** The test for choosing color. */
    private final BooleanSupplier source;

    /**
     * Constructor.
     * 
     * @param trueColor The color for the true state.
     * @param falseColor The color for the false state.
     * @param supplier The condition to check.
     */
    public IndicatorPattern(final Color trueColor, final Color falseColor,
            final BooleanSupplier supplier) {
        super();
        this.trueColor = trueColor;
        this.falseColor = falseColor;
        this.source = supplier;
    }

    /**
     * Constructor.
     * 
     * @param trueColor The color for the true state.
     * @param falseColor The color for the false state.
     * @param topic The Network Tables topic to check.
     */
    public IndicatorPattern(final Color trueColor, final Color falseColor, final String topic) {
        super();
        final NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
        final BooleanSubscriber table = ntinst.getBooleanTopic(topic).subscribe(false);
        this.trueColor = trueColor;
        this.falseColor = falseColor;
        this.source = table::get;
    }

    @Override
    public void initialize(final SegmentBuffer buffer) {
        // Do nothing
    }

    @Override
    public void update(final SegmentBuffer buffer) {
        buffer.setAll(this.source.getAsBoolean() ? this.trueColor : this.falseColor);
    }

    @Override
    public String toString() {
        return String.format("IndicatorPattern(%s, %s)", this.trueColor, this.falseColor);
    }
}
