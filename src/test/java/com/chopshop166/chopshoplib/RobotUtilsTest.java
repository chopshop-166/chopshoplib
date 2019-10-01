package com.chopshop166.chopshoplib;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

/**
 * Utilities that are related to overall robot functionality.
 */
public final class RobotUtilsTest extends TestCase {

    static private final String PACKAGE = "com.chopshop166";

    @Test
    public void testMapA() {
        final RobotMap map = RobotUtils.getMapForName("A", RobotMap.class, PACKAGE);
        assertThat("Found Map A", map.doStuff(), is("MapA"));
    }

    @Test
    public void testMapB() {
        final RobotMap map = RobotUtils.getMapForName("B", RobotMap.class, PACKAGE);
        assertThat("Found Map B", map.doStuff(), is("MapB"));
    }

    @Test
    public void testBadName() {
        final RobotMap map = RobotUtils.getMapForName("C", RobotMap.class, PACKAGE);
        assertNull("Map is null", map);
    }

    @Test
    public void testBadNameDefault() {
        final RobotMap defMap = new MapA();
        final RobotMap map = RobotUtils.getMapForName("C", RobotMap.class, PACKAGE, defMap);
        assertThat("Map is default value", map, is(defMap));
    }
}