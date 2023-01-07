package com.chopshop166.chopshoplib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.chopshop166.chopshoplib.digital.DigitalInputSource;

/** Test that DigitalInputSource is assigned properly. */
final class DigitalInputSourceTest {

    /** Get the map for A. */
    @Test
    /* package */ void testLambda() {
        final DigitalInputSource source = () -> true;
        Assertions.assertTrue(source.getAsBoolean(), "Lambda is assigned to the correct function");
    }
}
