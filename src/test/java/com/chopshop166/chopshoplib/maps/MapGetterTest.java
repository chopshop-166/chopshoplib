package com.chopshop166.chopshoplib.maps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.chopshop166.chopshoplib.commands.CommandRobot;
import com.chopshop166.chopshoplib.maps.RobotMap.MapType;

import org.junit.jupiter.api.Test;

/**
 * Utilities that are related to overall robot functionality.
 */
final class MapGetterTest {

    /** The package to use. */
    static private final String PACKAGE = "com.chopshop166";

    /** Get the map for A. */
    @Test
    /* package */ void testMapA() {
        final RobotMap map = CommandRobot.getMapForName("A", RobotMap.class, PACKAGE);
        assertEquals(MapType.A, map.getType(), "Gets a map of type A");
    }

    /** Get the map for B. */
    @Test
    /* package */ void testMapB() {
        final RobotMap map = CommandRobot.getMapForName("B", RobotMap.class, PACKAGE);
        assertEquals(MapType.B, map.getType(), "Gets a map of type B");
    }

    /** Check handling of an invalid name. */
    @Test
    /* package */ void testBadName() {
        final RobotMap map = CommandRobot.getMapForName("C", RobotMap.class, PACKAGE);
        assertNull(map, "Does not get a valid map");
    }

    /** Check handling of an invalid name with a default. */
    @Test
    /* package */ void testBadNameDefault() {
        final RobotMap defMap = new MapA();
        final RobotMap map = CommandRobot.getMapForName("C", RobotMap.class, PACKAGE, defMap);
        assertEquals(defMap, map, "Gets the default map");
    }
}