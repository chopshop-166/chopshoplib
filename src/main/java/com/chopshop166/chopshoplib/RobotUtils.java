package com.chopshop166.chopshoplib;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.function.BooleanSupplier;
import java.util.stream.DoubleStream;

import com.google.common.reflect.ClassPath;

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
            NetworkInterface iface = NetworkInterface.getByName("eth0");
            byte[] mac = iface.getHardwareAddress();

            if (mac == null) { // happens on windows sometimes
                throw new SocketException();
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            return sb.toString();
        } catch (SocketException e) {
            return "SocketException";
        }
    }

    /**
     * Get a RobotMap for the given name.
     * 
     * @param name      The name to match against in annotations.
     * @param rootClass The root class object that the map derives from.
     */
    public static <T> T getMapForName(final String name, final Class<T> rootClass, final String pkg) {
        return getMapForName(name, rootClass, pkg, null);
    }

    /**
     * Get a RobotMap for the given name.
     * 
     * @param name         The name to match against in annotations.
     * @param rootClass    The root class object that the map derives from.
     * @param defaultValue The object to return if no match is found.
     */
    public static <T> T getMapForName(final String name, final Class<T> rootClass, final String pkg,
            final T defaultValue) {
        try {
            // scans the class path used by classloader
            final ClassPath classpath = ClassPath.from(rootClass.getClassLoader());
            // Get class info for all classes
            for (final ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive(pkg)) {
                final Class<?> clazz = classInfo.load();
                // Make sure the class is derived from rootClass
                if (rootClass.isAssignableFrom(clazz)) {
                    // Cast the class to the derived type
                    final Class<? extends T> theClass = clazz.asSubclass(rootClass);
                    // Find all annotations that provide a name
                    for (final RobotMapFor annotation : clazz.getAnnotationsByType(RobotMapFor.class)) {
                        // Check to see if the name matches
                        if (annotation.value().equals(name)) {
                            return theClass.getDeclaredConstructor().newInstance();
                        }
                    }
                }
            }
        } catch (IOException | ReflectiveOperationException err) {
            return defaultValue;
        }
        return defaultValue;
    }
}