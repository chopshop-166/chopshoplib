package com.chopshop166.chopshoplib.boxes;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

/**
 * Box for storing a double.
 */
public class DoubleBox implements DoubleSupplier, DoubleConsumer {
    /** The data to store */
    private double data;

    @Override
    public void accept(final double value) {
        this.data = value;
    }

    @Override
    public double getAsDouble() {
        return this.data;
    }
}
