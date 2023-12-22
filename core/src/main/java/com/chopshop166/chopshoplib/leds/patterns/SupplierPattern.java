package com.chopshop166.chopshoplib.leds.patterns;

import java.util.function.Supplier;
import com.chopshop166.chopshoplib.leds.Pattern;
import com.chopshop166.chopshoplib.leds.SegmentBuffer;
import edu.wpi.first.wpilibj.util.Color;

/** Show one color when a state is true, and another when the state is false. */
public class SupplierPattern extends Pattern {
    /** The color source. */
    private final Supplier<Color> source;

    /**
     * Constructor.
     * 
     * @param source The source of color.
     */
    public SupplierPattern(final Supplier<Color> source) {
        super();
        this.source = source;
    }

    @Override
    public void initialize(final SegmentBuffer buffer) {
        // Do nothing
    }

    @Override
    public void update(final SegmentBuffer buffer) {
        buffer.setAll(this.source.get());
    }

    @Override
    public String toString() {
        return "SupplierPattern()";
    }
}
