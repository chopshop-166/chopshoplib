package com.chopshop166.chopshoplib.boxes;

import java.util.function.BooleanSupplier;
import edu.wpi.first.util.function.BooleanConsumer;

/**
 * Box for storing a boolean.
 */
public class BooleanBox implements BooleanSupplier, BooleanConsumer {
    /** The data to store */
    private boolean data;

    /** Enable a flag in the data. */
    public void flag() {
        accept(true);
    }

    /** Enable a flag in the data. */
    public void reset() {
        accept(false);
    }

    @Override
    public void accept(final boolean value) {
        this.data = value;
    }

    @Override
    public boolean getAsBoolean() {
        return this.data;
    }
}
