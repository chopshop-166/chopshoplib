package com.chopshop166.chopshoplib.motors.validators;

import java.util.function.BooleanSupplier;

public interface MotorValidator extends BooleanSupplier {
    public void reset();
}
