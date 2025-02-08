package com.chopshop166.chopshoplib.motors.validators;

import java.util.function.DoubleSupplier;
import com.chopshop166.chopshoplib.PersistenceCheck;

/**
 * A validator that uses a motor's encoder rate to determine if the motor is currently spinning
 */
public class EncoderValidator implements MotorValidator {

    /** The threshold for if the encoder is not rotating */
    private final double rateThreshold;
    /** A function that returns a motor's encoder rate */
    private final DoubleSupplier encoderRate;
    /** Persistance check that determines if the encoder is not spinning */
    private final PersistenceCheck persistenceCheck;

    /**
     * Construct an encoder validator
     * 
     * @param encoderRate a function that returns a motor's encoder rate
     * @param rateThreshold the threshold for if the encoder is not rotating
     * @param persistance how many cycles to determine if the encoder is not rotating
     */
    public EncoderValidator(final DoubleSupplier encoderRate, final double rateThreshold,
            final int persistance) {
        this.encoderRate = encoderRate;
        this.rateThreshold = rateThreshold;
        this.persistenceCheck = new PersistenceCheck(persistance,
                () -> Math.abs(this.encoderRate.getAsDouble()) <= this.rateThreshold);
    }

    /**
     * Check if the encoder rate is over the threshold
     */
    @Override
    public boolean getAsBoolean() {
        return this.persistenceCheck.getAsBoolean();
    }

    /**
     * Reset the validator
     */
    @Override
    public void reset() {
        this.persistenceCheck.reset();
    }
}
