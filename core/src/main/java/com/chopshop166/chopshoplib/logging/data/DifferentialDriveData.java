package com.chopshop166.chopshoplib.logging.data;

import com.chopshop166.chopshoplib.logging.DataWrapper;

/**
 * Differential Drive Data
 *
 * Provides an interface for getting and setting data in the Different Drive Map. This implemnts the
 * loggable IO layer that AdvantageKit expects and provides a snapshot of sensor input
 */
public class DifferentialDriveData extends DataWrapper {

    /** The left motor controller (or set). */
    public MotorControllerData left = new MotorControllerData();
    /** The right motor controller (or set). */
    public MotorControllerData right = new MotorControllerData();

    /** The Yaw angle of the robot in Degrees */
    public double gyroYawAngleDegrees;
}
