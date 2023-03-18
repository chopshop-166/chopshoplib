package com.chopshop166.chopshoplib.motors.validators;

import java.util.function.BooleanSupplier;

/**
 * A check to see if a motor is spinning correctly
 */
public interface MotorValidator extends BooleanSupplier {
    /** Reset validator */
    default void reset() {}
}
