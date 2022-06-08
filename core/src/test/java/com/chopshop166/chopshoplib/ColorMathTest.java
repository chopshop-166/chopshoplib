package com.chopshop166.chopshoplib;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.wpi.first.wpilibj.util.Color;

/** Tests that the color math is working properly */
final class ColorMathTest {

    /** The acceptable error for equality */
    private static final double EPSILON = 0.01;

    @Test
    /* package */ void testEquality() {
        final Color colorLeft = new Color(0.1, 0.2, 0.3);
        final Color colorRight = new Color(0.1, 0.2, 0.3);

        assertTrue(ColorMath.equals(colorLeft, colorRight, EPSILON), "Two colors are equal");
    }

    @Test
    /* package */ void testAddition() {
        final Color colorLeft = new Color(0.1, 0.2, 0.3);
        final Color colorRight = new Color(0.2, 0.4, 0.6);
        final Color expected = new Color(0.3, 0.6, 0.9);

        assertTrue(ColorMath.equals(ColorMath.plus(colorLeft, colorRight), expected,
                EPSILON),
                "Color addition returns correct value");

    }
}
