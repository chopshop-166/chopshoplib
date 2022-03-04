package com.chopshop166.chopshoplib.states;

import java.util.function.UnaryOperator;

/**
 * Direction for things such as a roller.
 */
public enum LinearDirection implements UnaryOperator<Double> {
    // Reverse Direction
    REVERSE {
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
        public Double apply(final Double value) {
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
        public Double apply(final Double value) {
            return value;
        }
    };
}
