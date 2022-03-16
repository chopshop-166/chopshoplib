package com.chopshop166.chopshoplib.sensors.gyro;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/** Gyro wrapper. */
public interface SmartGyro extends Gyro, Sendable {

    /**
     * Set the current angle.
     * 
     * @param angle The current angle of the gyro, in degrees.
     */
    void setAngle(double angle);
}
