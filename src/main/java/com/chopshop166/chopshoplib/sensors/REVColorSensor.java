package com.chopshop166.chopshoplib.sensors;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C.Port;

/**
 * REV color sensor that implements our interface.
 */
public class REVColorSensor extends ColorSensorV3 implements IColorSensor {

    public REVColorSensor(final Port port) {
        super(port);
    }

}