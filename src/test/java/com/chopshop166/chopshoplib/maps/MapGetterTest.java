package com.chopshop166.chopshoplib.maps;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.chopshop166.chopshoplib.commands.CommandRobot;

import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

/**
 * Utilities that are related to overall robot functionality.
 */
public final class MapGetterTest extends TestCase {

    /** The package to use. */
    static private final String PACKAGE = "com.chopshop166";

    /** Get the map for A. */
    @Test
    public void testMapA() {
        final RobotMap map = CommandRobot.getMapForName("A", RobotMap.class, PACKAGE);
        assertThat("Found Map A", map.doStuff(), is("MapA"));
    }

    /** Get the map for B. */
    @Test
    public void testMapB() {
        final RobotMap map = CommandRobot.getMapForName("B", RobotMap.class, PACKAGE);
        assertThat("Found Map B", map.doStuff(), is("MapB"));
    }

    /** Check handling of an invalid name. */
    @Test
    public void testBadName() {
        final RobotMap map = CommandRobot.getMapForName("C", RobotMap.class, PACKAGE);
        assertNull("Map is null", map);
    }

    /** Check handling of an invalid name with a default. */
    @Test
    public void testBadNameDefault() {
        final RobotMap defMap = new MapA();
        final RobotMap map = CommandRobot.getMapForName("C", RobotMap.class, PACKAGE, defMap);
        assertThat("Map is default value", map, is(defMap));
    }
}