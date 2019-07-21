package com.chopshop166.chopshoplib;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

import org.hamcrest.core.IsNull;

import static org.hamcrest.CoreMatchers.*;
import junit.framework.TestCase;

/**
 * Utilities that are related to overall robot functionality.
 */
public final class RobotUtilsTest extends TestCase {
    @Test
    public void testMapA() {
        final RobotMap map = RobotUtils.getMapForName("A", RobotMap.class, "com.chopshop166");
        assertThat("Found Map A", map.doStuff(), is("MapA"));
    }

    @Test
    public void testMapB() {
        final RobotMap map = RobotUtils.getMapForName("B", RobotMap.class, "com.chopshop166");
        assertThat("Found Map B", map.doStuff(), is("MapB"));
    }

    @Test
    public void testBadName() {
        final RobotMap map = RobotUtils.getMapForName("C", RobotMap.class, "com.chopshop166");
        assertNull("Map is null", map);
    }

    @Test
    public void testBadNameDefault() {
        final RobotMap defMap = new MapA();
        final RobotMap map = RobotUtils.getMapForName("C", RobotMap.class, "com.chopshop166", defMap);
        assertThat("Map is default value", map, is(defMap));
    }
}