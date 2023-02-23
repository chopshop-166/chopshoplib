package com.chopshop166.chopshoplib.motors.validators;

import java.util.function.DoubleSupplier;

public class EncoderValidator implements MotorValidator {

    private final double rateThreshold;
    private final DoubleSupplier encoderRate;

    public EncoderValidator(final DoubleSupplier encoderRate, final double rateThreshold) {
        this.encoderRate = encoderRate;
        this.rateThreshold = rateThreshold;
    }


    public boolean getAsBoolean() {
        return Math.abs(encoderRate.getAsDouble()) >= rateThreshold;
    }

    public void reset() {

    }
}
