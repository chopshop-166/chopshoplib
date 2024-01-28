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
    public double clamp(final double value) {
        return Math.max(this.min, Math.min(value, this.max));
    }

    /**
     * Test if the range contains a number.
     * 
     * @param value The value to test.
     * @return A boolean.
     */
    public boolean contains(final double value) {
        return this.min <= value && value <= this.max;
    }

    /**
     * Filter a speed through this range.
     * 
     * If the value is higher than the max or lower than the min, then stop. Otherwise, it's normal.
     * 
     * @param value The measurement to compare with the range.
     * @param speed The speed percent to apply to the measurement.
     * @return A new speed percent value.
     */
    public double filterSpeed(final double value, final double speed) {
        return this.scaleSpeed(value, speed, 0.0);
    }

    /**
     * Scale a speed through this range.
     * 
     * If the value is higher than the max or lower than the min, then use a reduced speed.
     * Otherwise, it's normal.
     * 
     * @param value The measurement to compare with the range.
     * @param speed The speed percent to apply to the measurement.
     * @param modifier The modifier if the value is out of range.
     * @return A new speed percent value.
     */
    public double scaleSpeed(final double value, final double speed, final double modifier) {
        if (value > this.max && speed > 0.0 || value < this.min && speed < 0.0) {
            return speed * modifier;
        } else {
            return speed;
        }
    }
}
