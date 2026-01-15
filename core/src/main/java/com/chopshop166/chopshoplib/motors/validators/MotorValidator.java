package com.chopshop166.chopshoplib.motors.validators;

import java.util.function.BooleanSupplier;

/**
 * A check to see if a motor is spinning correctly
 */
@FunctionalInterface
public interface MotorValidator extends BooleanSupplier {
    /** Reset validator */
    default void reset() {
        /* Nothing to reset by default */
    }
}
