package com.chopshop166.chopshoplib.sensors.gyro;

import edu.wpi.first.math.geometry.Rotation3d;
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

    /**
     * Return the heading of the robot as a Rotation3d
     *
     * @return A Rotation3d object containing the current angle of the gyro
     */
    Rotation3d getRotation3d();

    /**
     * Return the rotational velocity as a Rotation3d
     * 
     * @return A Rotation3d object containing the rotational velocity
     */
    Rotation3d getRotationalVelocity();
}
