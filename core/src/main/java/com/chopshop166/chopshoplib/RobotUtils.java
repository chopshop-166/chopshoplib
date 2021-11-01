package com.chopshop166.chopshoplib;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.StringJoiner;
import java.util.function.BooleanSupplier;
import java.util.stream.DoubleStream;

import edu.wpi.first.wpilibj.Preferences;

/**
 * Utilities that are related to overall robot functionality.
 */
public final class RobotUtils {
    private RobotUtils() {
    }
    
    /**
     * Get a value if it exists, or a default value if it's null.
     * 
     * @param <T>          The type to get.
     * @param value        The value to test.
     * @param defaultValue The default value for if value is null.
     * @return A null safe value
     */
    public static <T> T getValueOrDefault(final T value, final T defaultValue) {
        return value == null ? defaultValue : value;
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
     * Clamp a value between bounds.
     * 
     * @param minBound The lowest possible value.
     * @param maxBound The highest possible value.
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
     * @param maxBound The highest possible value.
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
     * @param maxBound The highest possible value.
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
     * @param maxBound The highest possible value.
     * @param value    The value to clamp.
     * @return The provided value, clamped between minBound and maxBound.
     */
    public static long clamp(final long minBound, final long maxBound, final long value) {
        return Math.max(minBound, Math.min(value, maxBound));
    }

    /**
     * Sign Preserving Square
     * 
     * @param value The value to multiply.
     * @return The square of the given value, preserving sign.
     */
    public static double sps(final double value) {
        return Math.copySign(value * value, value);
    }

    /**
     * Sign Preserving Power
     * 
     * @param value The value to multiply.
     * @param exp   The value to exponentiate by.
     * @return The square of the given value, preserving sign.
     */
    public static double sppow(final double value, final double exp) {
        return Math.copySign(Math.pow(value, exp), value);
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

    /**
     * Get the MAC address
     * 
     * @return The robot's MAC address as a string.
     */
    public static String getMACAddress() {
        try {
            final NetworkInterface iface = NetworkInterface.getByName("eth0");
            final byte[] mac = iface.getHardwareAddress();

            if (mac == null) { // happens on windows sometimes
                throw new SocketException();
            }

            final StringJoiner sb = new StringJoiner(":");
            for (final byte octet : mac) {
                sb.add(String.format("%02X", octet));
            }
            return sb.toString();
        } catch (SocketException e) {
            return "SocketException";
        }
    }
}