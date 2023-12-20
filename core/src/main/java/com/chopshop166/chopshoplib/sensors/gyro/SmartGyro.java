package com.chopshop166.chopshoplib.sensors.gyro;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;

/** Gyro wrapper. */
public interface SmartGyro {
    /**
     * Calibrate the gyro. It's important to make sure that the robot is not moving while the
     * calibration is in progress, this is typically done when the robot is first turned on while
     * it's sitting at rest before the match starts.
     */
    void calibrate();

    /**
     * Reset the gyro. Resets the gyro to a heading of zero. This can be used if there is
     * significant drift in the gyro, and it needs to be recalibrated after it has been running.
     */
    void reset();

    /**
     * Return the heading of the robot in degrees.
     *
     * <p>
     * The angle is continuous, that is it will continue from 360 to 361 degrees. This allows
     * algorithms that wouldn't want to see a discontinuity in the gyro output as it sweeps past
     * from 360 to 0 on the second time around.
     *
     * <p>
     * The angle is expected to increase as the gyro turns clockwise when looked at from the top. It
     * needs to follow the NED axis convention.
     *
     * <p>
     * This heading is based on integration of the returned rate from the gyro.
     *
     * @return the current heading of the robot in degrees.
     */
    double getAngle();

    /**
     * Return the rate of rotation of the gyro.
     *
     * <p>
     * The rate is based on the most recent reading of the gyro analog value
     *
     * <p>
     * The rate is expected to be positive as the gyro turns clockwise when looked at from the top.
     * It needs to follow the NED axis convention.
     *
     * @return the current rate in degrees per second
     */
    double getRate();

    /**
     * Return the heading of the robot as a {@link edu.wpi.first.math.geometry.Rotation2d}.
     *
     * <p>
     * The angle is continuous, that is it will continue from 360 to 361 degrees. This allows
     * algorithms that wouldn't want to see a discontinuity in the gyro output as it sweeps past
     * from 360 to 0 on the second time around.
     *
     * <p>
     * The angle is expected to increase as the gyro turns counterclockwise when looked at from the
     * top. It needs to follow the NWU axis convention.
     *
     * <p>
     * This heading is based on integration of the returned rate from the gyro.
     *
     * @return the current heading of the robot as a {@link edu.wpi.first.math.geometry.Rotation2d}.
     */
    default Rotation2d getRotation2d() {
        return Rotation2d.fromDegrees(-this.getAngle());
    }

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
    default Rotation3d getRotation3d() {
        return new Rotation3d(0, 0, this.getAngle());
    }

    /**
     * Return the rotational velocity as a Rotation3d
     * 
     * @return A Rotation3d object containing the rotational velocity
     */
    default Rotation3d getRotationalVelocity() {
        return new Rotation3d(0, 0, this.getRate());
    }
}
