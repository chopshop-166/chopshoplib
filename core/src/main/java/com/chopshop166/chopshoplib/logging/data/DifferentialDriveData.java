package com.chopshop166.chopshoplib.logging.data;

import com.chopshop166.chopshoplib.logging.DataWrapper;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;

/**
 * Differential Drive Data
 *
 * Provides an interface for getting and setting data in the Different Drive Map. This implemnts the
 * loggable IO layer that AdvantageKit expects and provides a snapshot of sensor input.
 */
public class DifferentialDriveData extends DataWrapper {

    /** The left motor controller (or set). */
    public MotorControllerData left = new MotorControllerData();
    /** The right motor controller (or set). */
    public MotorControllerData right = new MotorControllerData();

    /** The Yaw angle of the robot in Degrees */
    public double gyroYawAngleDegrees;

    /**
     * Use arcade style drive.
     * 
     * @param xSpeed The x speed.
     * @param zRotation The robot rotation.
     */
    public void arcadeDrive(final double xSpeed, final double zRotation) {
        final WheelSpeeds speeds = DifferentialDrive.arcadeDriveIK(xSpeed, zRotation, true);
        this.left.setpoint = speeds.left;
        this.right.setpoint = speeds.right;
    }

    /**
     * Use tank style drive.
     * 
     * @param leftSpeed The left motor speed.
     * @param rightSpeed The right motor speed.
     */
    public void tankDrive(final double leftSpeed, final double rightSpeed) {
        final WheelSpeeds speeds = DifferentialDrive.tankDriveIK(leftSpeed, rightSpeed, false);
        this.left.setpoint = speeds.left;
        this.right.setpoint = speeds.right;
    }
}
