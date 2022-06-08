package com.chopshop166.chopshoplib;

import edu.wpi.first.wpilibj.util.Color;

/**
 * A utility class for doing vector math with the Color object.
 */
public final class ColorMath {

    private ColorMath() {

    }

    /**
     * Adds two colors together.
     * 
     * @param lhs Left-hand side
     * @param rhs Right-hand side
     * @return lhs + rhs
     */
    public static Color plus(final Color lhs, final Color rhs) {
        return new Color(lhs.red + rhs.red, lhs.green + rhs.green, lhs.blue + rhs.blue);
    }

    /**
     * Subtract two colors.
     * 
     * @param lhs Left-hand side
     * @param rhs Right-hand side
     * @return lhs - rhs
     */
    public static Color minus(final Color lhs, final Color rhs) {
        return new Color(lhs.red - rhs.red, lhs.green - rhs.green, lhs.blue - rhs.blue);
    }

    /**
     * Multiply a color by a scalar value.
     * 
     * @param color  Color that gets multiplied by the scalar
     * @param scalar Number that multiplies the color
     * @return color * scalar
     */
    public static Color times(final Color color, final double scalar) {
        return new Color(color.red * scalar, color.green * scalar, color.blue * scalar);
    }

    /**
     * Divide a color by a scalar value.
     * 
     * @param color  Color that gets divided by the scalar
     * @param scalar Number that divides the color
     * @return color / scalar
     */
    public static Color div(final Color color, final double scalar) {
        return new Color(color.red / scalar, color.green / scalar, color.blue / scalar);
    }

    /**
     * Takes the dot product of two colors.
     * 
     * @param lhs Left-hand side
     * @param rhs Right-hand side
     * @return lhs * rhs
     */
    public static double dot(final Color lhs, final Color rhs) {
        return lhs.red * rhs.red + lhs.green * rhs.green + lhs.blue * rhs.blue;
    }

    /**
     * Interpolates smoothly between two colors.
     * 
     * @param start  the starting color
     * @param end    the ending color
     * @param factor the interpolation factor (0 results in the start color, 1
     *               results in the end color, going beyond 0 or 1 will extrapolate
     *               past the start and end colors)
     * @return A blend of the two colors based on the factor
     */
    public static Color lerp(final Color start, final Color end, final double factor) {
        return plus(times(minus(end, start), factor), start);
    }
}
