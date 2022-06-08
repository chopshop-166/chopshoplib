package com.chopshop166.chopshoplib;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.wpi.first.wpilibj.util.Color;

/** Tests that the color math is working properly */
final class ColorMathTest {

    /** The acceptable error for equality */
    private static final double EPSILON = 0.001;

    /** Check color equality */
    @Test
    /* package */ void testEquality() {
        final Color colorLeft = new Color(0.1, 0.2, 0.3);
        final Color colorRight = new Color(0.1, 0.2, 0.3);

        assertTrue(ColorMath.equals(colorLeft, colorRight, EPSILON), "Two colors are equal");
    }

    /** Check color addition */
    @Test
    /* package */ void testAddition() {
        final Color colorLeft = new Color(0.1, 0.2, 0.3);
        final Color colorRight = new Color(0.2, 0.4, 0.6);
        final Color expected = new Color(0.3, 0.6, 0.9);

        assertTrue(ColorMath.equals(ColorMath.plus(colorLeft, colorRight), expected,
                EPSILON),
                "Color addition returns correct value");
    }

    /** Check color subtraction */
    @Test
    /* package */ void testSubtraction() {
        final Color colorLeft = new Color(0.3, 0.6, 0.9);
        final Color colorRight = new Color(0.1, 0.2, 0.3);
        final Color expected = new Color(0.2, 0.4, 0.6);

        assertTrue(ColorMath.equals(ColorMath.minus(colorLeft, colorRight), expected,
                EPSILON),
                "Color subtraction returns correct value");
    }

    /** Check color multiplication */
    @Test
    /* package */ void testMultiplication() {
        final Color colorLeft = new Color(0.3, 0.6, 0.9);
        final double scalarRight = 0.5;
        final Color expected = new Color(0.15, 0.3, 0.45);

        assertTrue(ColorMath.equals(ColorMath.times(colorLeft, scalarRight), expected,
                EPSILON),
                "Color multiplication returns correct value");
    }

    /** Check color division */
    @Test
    /* package */ void testDivision() {
        final Color colorLeft = new Color(0.3, 0.6, 0.9);
        final double scalarRight = 2.0;
        final Color expected = new Color(0.15, 0.3, 0.45);

        assertTrue(ColorMath.equals(ColorMath.div(colorLeft, scalarRight), expected,
                EPSILON),
                "Color multiplication returns correct value");
    }

    /** Check color dot product */
    @Test
    /* package */ void testDotProduct() {
        final Color colorLeft = new Color(0.3, 0.6, 0.9);
        final Color colorRight = new Color(0.3, 0.6, 0.9);
        final double expected = 1.26;

        assertTrue(Math.abs(expected - ColorMath.dot(colorLeft, colorRight)) <= EPSILON,
                "Color dot product returns correct value");
    }

    /** Check color interpolation */
    @Test
    /* package */ void testLerp() {
        final Color colorStart = new Color(0.2, 0.4, 0.15);
        final Color colorEnd = new Color(0.85, 0.2, 0.9);

        final Color expected = new Color(0.525, 0.3, 0.525);

        final Color result = ColorMath.lerp(colorStart, colorEnd, 0.5);

        assertTrue(ColorMath.equals(
                result,
                expected,
                EPSILON),
                "Color interpolation returns correct value");
    }

}
