package com.chopshop166.chopshoplib.maps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.chopshop166.chopshoplib.commands.CommandRobot;
import com.chopshop166.chopshoplib.maps.RobotMap.MapType;

/**
 * Utilities that are related to overall robot functionality.
 */
final class MapGetterTest {

    /** The package to use. */
    private static final String PACKAGE = "com.chopshop166";

    /** Get the map for A. */
    @Test
    /* package */ void testMapA() {
        final RobotMap map = CommandRobot.getMapForName("A", RobotMap.class, PACKAGE);
        Assertions.assertEquals(MapType.A, map.getType(), "Gets a map of type A");
    }

    /** Get the map for B. */
    @Test
    /* package */ void testMapB() {
        final RobotMap map = CommandRobot.getMapForName("B", RobotMap.class, PACKAGE);
        Assertions.assertEquals(MapType.B, map.getType(), "Gets a map of type B");
    }

    /** Check handling of an invalid name. */
    @Test
    /* package */ void testBadName() {
        final RobotMap map = CommandRobot.getMapForName("C", RobotMap.class, PACKAGE);
        Assertions.assertNull(map, "Does not get a valid map");
    }

    /** Check handling of an invalid name with a default. */
    @Test
    /* package */ void testBadNameDefault() {
        final RobotMap defMap = new MapA();
        final RobotMap map = CommandRobot.getMapForName("C", RobotMap.class, PACKAGE, defMap);
        Assertions.assertEquals(defMap, map, "Gets the default map");
    }
}
