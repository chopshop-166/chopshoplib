package com.chopshop166.chopshoplib;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import junit.framework.TestCase;

/**
 * Utilities that are related to overall robot functionality.
 */
public final class RobotUtilsTest extends TestCase {
    @Test
    public void testMapA() {
        final RobotMap map = RobotUtils.getMapForName("A", RobotMap.class);
        assertThat("Found Map A", map.doStuff(), is("MapA"));
    }
}