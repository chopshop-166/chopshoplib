package com.chopshop166.chopshoplib.boxes;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

/**
 * Box for storing an integer.
 */
public class IntegerBox implements IntSupplier, IntConsumer {
    /** The data to store */
    private int data;

    /** Constructor. */
    public IntegerBox() {
        // This line intentionally left blank
    }

    /**
     * Constructor.
     * 
     * @param value The starting value for the box.
     */
    public IntegerBox(final int value) {
        this.data = value;
    }

    @Override
    public void accept(final int value) {
        this.data = value;
    }

    @Override
    public int getAsInt() {
        return this.data;
    }
}
