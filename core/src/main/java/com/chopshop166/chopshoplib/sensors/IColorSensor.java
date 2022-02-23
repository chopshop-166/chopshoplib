package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.util.Color;

/**
 * Interface for a color sensor.
 */
public interface IColorSensor {

    /**
     * Get the color from the sensor.
     * 
     * @return The color.
     */
    Color getColor();

    /**
     * Get the distance from the color sensor to the detected object.
     * 
     * @return Proximity measurement value.
     */
    default int getProximity() {
        return 0;
    }
}