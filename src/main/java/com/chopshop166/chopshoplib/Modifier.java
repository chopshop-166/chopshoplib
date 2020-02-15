package com.chopshop166.chopshoplib;

import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface Modifier extends Function<Double, Double> {

    /**
     * Modifier to prevent going above a certain height.
     * 
     * @param limit The trigger for being at max height.
     * @return The new speed.
     */
    public static Modifier upperLimit(final BooleanSupplier limit) {
        return speedFilter((Double speed) -> speed > 0.0 && limit.getAsBoolean());
    }

    /**
     * Modifier to prevent going below a certain height.
     * 
     * @param limit The trigger for being at min height.
     * @return The new speed.
     */
    public static Modifier lowerLimit(final BooleanSupplier limit) {
        return speedFilter((Double speed) -> speed < 0.0 && limit.getAsBoolean());
    }

    /**
     * Modifier to set the speed to 0 if within a range.
     * 
     * @param band The minimum value to allow the absolute value of.
     * @return The new speed.
     */
    public static Modifier deadband(final double band) {
        return speedFilter((Double speed) -> Math.abs(speed) < band);
    }

    /**
     * Creates a modifier that sets speed to 0 if the condition is true.
     * 
     * @param condition The condition to check.
     * @return The original speed, or 0 if the condition is true.
     */
    public static Modifier speedFilter(final Predicate<Double> condition) {
        return (Double speed) -> condition.test(speed) ? 0.0 : speed;
    }
}