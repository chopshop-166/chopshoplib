package com.chopshop166.chopshoplib.states;

import java.util.function.UnaryOperator;

/**
 * Enumeration to represent direction of rotation.
 */
public enum SpinDirection implements UnaryOperator<Double> {
    /** Clockwise Direction */
    CLOCKWISE {
        /**
         * Get a value (usually speed) adjusted for direction.
         * 
         * @param value The input value.
         * @return The same value.
         */
        @Override
        public Double apply(final Double value) {
            return value;
        }
    },
    /** Neutral (not moving) */
    NEUTRAL {
        /**
         * Get a value (usually speed) adjusted for direction.
         * 
         * @param value The input value.
         * @return No speed (0.0).
         */
        @Override
        public Double apply(final Double value) {
            return 0.0;
        }
    },
    /** Counterclockwise Direction */
    COUNTERCLOCKWISE {
        /**
         * Get a value (usually speed) adjusted for direction.
         * 
         * @param value The input value.
         * @return The inverted value.
         */
        @Override
        public Double apply(final Double value) {
            return -value;
        }
    };
}
