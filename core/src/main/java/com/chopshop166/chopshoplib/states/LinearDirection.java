package com.chopshop166.chopshoplib.states;

/**
 * Direction for things such as a roller.
 */
public enum LinearDirection {
    // Reverse Direction
    REVERSE {
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
    },
    // Neither forward nor reverse
    NEUTRAL {
        /**
         * Cancel a speed for neutral state.
         * 
         * @param value The input value.
         * @return No speed (0.0).
         */
        @Override
        public double get(final double value) {
            return 0.0;
        }
    },
    // Forward Direction
    FORWARD {
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
    };

    /**
     * Get the given speed, inverting if necessary.
     * 
     * @param value The input speed.
     * @return The input speed or its inverted value.
     */
    public abstract double get(final double value);
}
