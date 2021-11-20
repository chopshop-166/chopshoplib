package com.chopshop166.chopshoplib.states;

/**
 * Enumeration to represent direction of rotation.
 */
public enum SpinDirection {
    // Clockwise Direction
    CLOCKWISE {
        /**
         * Get a value (usually speed) adjusted for direction.
         * 
         * @param value The input value.
         * @return The same value.
         */
        @Override
        public double get(final double value) {
            return value;
        }
    },
    // Counterclockwise Direction
    COUNTERCLOCKWISE {
        /**
         * Get a value (usually speed) adjusted for direction.
         * 
         * @param value The input value.
         * @return The inverted value.
         */
        @Override
        public double get(final double value) {
            return -value;
        }
    };

    /**
     * Get the given speed, inverting if necessary.
     * 
     * @param value The input speed.
     * @return The input speed or its inverted value.
     */
    public abstract double get(final double value);
}