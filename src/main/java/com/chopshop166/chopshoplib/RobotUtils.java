package com.chopshop166.chopshoplib;

import java.lang.reflect.Field;
import java.util.function.BooleanSupplier;
import java.util.stream.DoubleStream;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotBase;

/**
 * Utilities that are related to overall robot functionality.
 */
public final class RobotUtils {
    private RobotUtils() {
    }

    /**
     * Clear all preferences from the robot.
     * <p>
     * This is a destructive operation.
     */
    public static void clearPreferences() {
        final Preferences prefs = Preferences.getInstance();
        for (final String key : prefs.getKeys()) {
            prefs.remove(key);
        }
    }

    /**
     * Map an array of {@code double} primitives to a {@link Double} array.
     * 
     * @param args The array of doubles to map.
     * @return The array of boxed values.
     */
    public static Double[] toBoxed(final double... args) {
        return DoubleStream.of(args).boxed().toArray(Double[]::new);
    }

    /**
     * Reset all {@link Resettable} objects within a given robot.
     * 
     * @param robot The robot to set members from.
     */
    public static void resetAll(final RobotBase robot) {
        final Class<? extends RobotBase> clazz = robot.getClass();

        for (final Field field : clazz.getDeclaredFields()) {
            // Make the field accessible, because apparently we're allowed to do that
            field.setAccessible(true);
            try {
                // See if the returned object implements resettable.
                // If it does, then reset it.
                // This should help prevent the robot from taking off unexpectedly
                if (Resettable.class.isAssignableFrom(field.getType())) {
                    final Resettable resettable = (Resettable) field.get(robot);
                    resettable.reset();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Clamp a value between bounds.
     * 
     * @param minBound The lowest possible value.
     * @param maxBound The highest possible value
     * @param value    The value to clamp.
     * @return The provided value, clamped between minBound and maxBound.
     */
    public static double clamp(final double minBound, final double maxBound, final double value) {
        return Math.max(minBound, Math.min(value, maxBound));
    }

    /**
     * Clamp a value between bounds.
     * 
     * @param minBound The lowest possible value.
     * @param maxBound The highest possible value
     * @param value    The value to clamp.
     * @return The provided value, clamped between minBound and maxBound.
     */
    public static float clamp(final float minBound, final float maxBound, final float value) {
        return Math.max(minBound, Math.min(value, maxBound));
    }

    /**
     * Clamp a value between bounds.
     * 
     * @param minBound The lowest possible value.
     * @param maxBound The highest possible value
     * @param value    The value to clamp.
     * @return The provided value, clamped between minBound and maxBound.
     */
    public static int clamp(final int minBound, final int maxBound, final int value) {
        return Math.max(minBound, Math.min(value, maxBound));
    }

    /**
     * Clamp a value between bounds.
     * 
     * @param minBound The lowest possible value.
     * @param maxBound The highest possible value
     * @param value    The value to clamp.
     * @return The provided value, clamped between minBound and maxBound.
     */
    public static long clamp(final long minBound, final long maxBound, final long value) {
        return Math.max(minBound, Math.min(value, maxBound));
    }

    /**
     * Negate a boolean supplier.
     * 
     * @param func The base function
     * @return A function that returns true when func returns false, and vice versa.
     */
    public static BooleanSupplier not(final BooleanSupplier func) {
        return () -> !func.getAsBoolean();
    }
}