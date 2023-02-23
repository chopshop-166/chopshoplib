package com.chopshop166.chopshoplib.motors.validators;

import java.util.function.DoubleSupplier;

/**
 * A validator that uses a motor's encoder rate to determine if the motor is currently spinning
 */
public class EncoderValidator implements MotorValidator {

    /** The threshold for if the encoder is not rotating */
    private final double rateThreshold;
    /** A function that returns a motor's encoder rate */
    private final DoubleSupplier encoderRate;

    /**
     * Construct an encoder validator
     * 
     * @param encoderRate a function that returns a motor's encoder rate
     * @param rateThreshold the threshold for if the encoder is not rotating
     */
    public EncoderValidator(final DoubleSupplier encoderRate, final double rateThreshold) {
        this.encoderRate = encoderRate;
        this.rateThreshold = rateThreshold;
    }

    /**
     * Check if the encoder rate is over the threshold
     */
    @Override
    public boolean getAsBoolean() {
        return Math.abs(encoderRate.getAsDouble()) >= rateThreshold;
    }

    /**
     * Reset the validator
     */
    @Override
    public void reset() {

    }
}
