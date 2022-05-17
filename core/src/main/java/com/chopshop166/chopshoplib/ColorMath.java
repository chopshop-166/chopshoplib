package com.chopshop166.chopshoplib;

import edu.wpi.first.wpilibj.util.Color;

public class ColorMath {

    public static Color plus(Color lhs, Color rhs) {
        return new Color(lhs.red + rhs.red, lhs.green + rhs.green, lhs.blue + rhs.blue);
    }

    public static Color minus(Color lhs, Color rhs) {
        return new Color(lhs.red - rhs.red, lhs.green - rhs.green, lhs.blue - rhs.blue);
    }

    public static Color times(Color color, double scalar) {
        return new Color(color.red * scalar, color.green * scalar, color.blue * scalar);
    }

    public static Color div(Color color, double scalar) {
        return new Color(color.red / scalar, color.green / scalar, color.blue / scalar);
    }

    public static double dot(Color lhs, Color rhs) {
        return lhs.red * rhs.red + lhs.green * rhs.green + lhs.blue * rhs.blue;
    }

    public static Color lerp(Color a, Color b, double factor) {
        return plus(times(minus(b, a), factor), a);
    }
}
