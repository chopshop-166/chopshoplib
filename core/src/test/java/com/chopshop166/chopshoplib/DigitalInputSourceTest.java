package com.chopshop166.chopshoplib;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.chopshop166.chopshoplib.sensors.DigitalInputSource;

import org.junit.jupiter.api.Test;

/** Test that DigitalInputSource is assigned properly. */
final class DigitalInputSourceTest {

    /** Get the map for A. */
    @Test
    /* package */ void testLambda() {
        final DigitalInputSource source = () -> true;
        assertTrue(source.getAsBoolean(), "Lambda is assigned to the correct function");
    }
}
