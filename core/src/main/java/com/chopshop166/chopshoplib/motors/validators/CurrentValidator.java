package com.chopshop166.chopshoplib.motors.validators;

import java.util.function.DoubleSupplier;
import edu.wpi.first.math.filter.LinearFilter;


/**
 * A validator that checks a motor's current draw against a threshold
 * 
 * Includes a low pass filter for the current measurements
 */
public class CurrentValidator implements MotorValidator {

    /** A function that returns the current draw */
    private final DoubleSupplier currentAmps;
    /** The current limit, in amps */
    private double limitAmps;
    /** The filter which filters the current draw measurements */
    private final LinearFilter filter;

    /**
     * Construct a current validator
     * 
     * @param currentAmps a function that supplies a motor's current draw, in amps
     * @param limitAmps the current limit in amps
     * @param filterCutoff the filter cutoff from 0.0 to 1.0
     */
    public CurrentValidator(final DoubleSupplier currentAmps, final double limitAmps,
            final double filterCutoff) {
        this.currentAmps = currentAmps;
        this.filter = LinearFilter.singlePoleIIR(1.0, filterCutoff);
        this.limitAmps = limitAmps;
    }

    /**
     * Set the current limit
     * 
     * @param limitAmps the current limit in amps
     */
    public void setLimit(final double limitAmps) {
        this.limitAmps = limitAmps;
    }

    /**
     * Check if the current draw is under the limit
     */
    @Override
    public boolean getAsBoolean() {
        return filter.calculate(currentAmps.getAsDouble()) < limitAmps;
    }

    /**
     * Reset the filter
     */
    @Override
    public void reset() {
        filter.reset();
    }
}
