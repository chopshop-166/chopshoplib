package com.chopshop166.chopshoplib.motors;

import java.util.function.BooleanSupplier;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;
import com.chopshop166.chopshoplib.SampleBuffer;
import com.google.common.math.Stats;

/**
 * A function from double to double, converting and limiting speeds.
 */
@FunctionalInterface
public interface Modifier extends DoubleUnaryOperator {

    /**
     * Modifier to prevent going above a certain value.
     * 
     * @param limit The trigger for being at max value.
     * @return The new speed.
     */
    static Modifier upperLimit(final BooleanSupplier limit) {
        return speedFilter(speed -> speed > 0.0 && limit.getAsBoolean());
    }

    /**
     * Modifier to prevent going below a certain value.
     * 
     * @param limit The trigger for being at min value.
     * @return The new speed.
     */
    static Modifier lowerLimit(final BooleanSupplier limit) {
        return speedFilter(speed -> speed < 0.0 && limit.getAsBoolean());
    }

    /**
     * Modifier to set the speed to 0 if within a range.
     * 
     * @param band The minimum value to allow the absolute value of.
     * @return The new speed.
     */
    static Modifier deadband(final double band) {
        return speedFilter(speed -> Math.abs(speed) < band);
    }

    /**
     * Apply a deadband to a value.
     *
     * Any value outside the deadband is scaled to the entire range.
     *
     * @param range The range to deaden.
     * @return A function taking and returning a double.
     */
    static Modifier scalingDeadband(final double range) {
        return speed -> {
            if (Math.abs(speed) < range) {
                return 0.0;
            } else {
                return (speed - (Math.signum(speed) * range)) / (1.0 - range);
            }
        };
    }

    /**
     * Modifier to set the speed based on a rolling average.
     * 
     * @param numSamples The number of samples to use.
     * @return The average speed.
     */
    static Modifier rollingAverage(final int numSamples) {
        /* The samples to average. */
        final var buffer = new SampleBuffer<Double>(numSamples);
        return speed -> {
            buffer.add(speed);
            return Stats.of(buffer).mean();
        };
    }

    /**
     * Use an exponent for the given speed.
     * 
     * Behaves as sign-preserving-square for exponent of 2.
     * 
     * @param exp The exponent to raise the speed to.
     * @return The new speed.
     */
    static Modifier power(final double exp) {
        return speed -> Math.copySign(Math.pow(speed, exp), speed);
    }

    /**
     * Modifier that inverts the given speed.
     * 
     * @return An inverter.
     */
    static Modifier invert() {
        return speed -> -speed;
    }

    /**
     * Creates a modifier that sets speed to 0 if the condition is true.
     * 
     * @param condition The condition to check.
     * @return The original speed, or 0 if the condition is true.
     */
    static Modifier speedFilter(final DoublePredicate condition) {
        return speed -> condition.test(speed) ? 0.0 : speed;
    }

    /**
     * Modifier that sets speed to 0 if a condition is true.
     * 
     * The condition doesn't have to be related to the input speed.
     * 
     * @param condition The condition to test for.
     * @return A modifier.
     */
    static Modifier unless(final BooleanSupplier condition) {
        return speed -> condition.getAsBoolean() ? 0.0 : speed;
    }
}
