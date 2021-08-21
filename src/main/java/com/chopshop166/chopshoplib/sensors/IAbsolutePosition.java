package com.chopshop166.chopshoplib.sensors;

/** Sensor that returns an absolute position. */
@FunctionalInterface
public interface IAbsolutePosition {

    /**
     * Get the absolute position, in rotations.
     * 
     * @return The absolute position.
     */
    double getAbsolutePosition();
}
