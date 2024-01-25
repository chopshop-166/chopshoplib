package com.chopshop166.chopshoplib;

/** Value range (min and max) */
public record ValueRange(double min, double max) {

    /**
     * Initializer.
     * 
     * The maximum value can not be less than the minimum value.
     * 
     * @param min The minimum value for the range.
     * @param max The maximum value for the range.
     */
    public ValueRange {
        if (min > max) {
            throw new IllegalArgumentException("min value must be less than max value");
        }
    }

    /**
     * Clamp a value within the range.
     * 
     * @param value The value to clamp.
     * @return The value, clamped within the range.
     */
    double clamp(final double value) {
        return Math.max(this.min, Math.min(value, this.max));
    }

    /**
     * Test if the range contains a number.
     * 
     * @param value The value to test.
     * @return A boolean.
     */
    boolean contains(final double value) {
        return this.min <= value && value <= this.max;
    }
}
