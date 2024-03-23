package com.chopshop166.chopshoplib.leds.patterns;

import java.util.function.DoubleSupplier;
import com.chopshop166.chopshoplib.leds.AnimatedPattern;
import com.chopshop166.chopshoplib.leds.SegmentBuffer;
import edu.wpi.first.wpilibj.util.Color;

/** Display a meter for a value from 0 to 1. */
public class MeterPattern extends AnimatedPattern {
    /** The color of the dot. */
    private final Color color;
    /** The meter data source. */
    private final DoubleSupplier supplier;

    /**
     * Constructor.
     * 
     * @param supp The source of data.
     */
    public MeterPattern(final DoubleSupplier supp) {
        this(Color.kGreen, supp);
    }

    /**
     * Constructor.
     * 
     * @param color The color of the dot.
     * @param supp The source of data.
     */
    public MeterPattern(final Color color, final DoubleSupplier supp) {
        super(0.05);
        this.color = color;
        this.supplier = supp;
    }

    @Override
    public void animate(final SegmentBuffer buffer) {
        buffer.setAll(Color.kBlack);
        final double pct = this.supplier.getAsDouble();
        for (int i = 0; i < pct * buffer.getLength(); i++) {
            buffer.set(i, this.color);
        }
    }

    @Override
    public String toString() {
        return String.format("MeterPattern(%s)", this.color.toString());
    }
}
