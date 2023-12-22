package com.chopshop166.chopshoplib.maps;

/**
 * Sample RobotMap base interface.
 */
public interface RobotMap {

    /** Enum for denoting different map values. */
    enum MapType {
        A, B, C
    }

    /**
     * Example function to get the map type.
     * 
     * @return A key value.
     */
    MapType getType();
}
