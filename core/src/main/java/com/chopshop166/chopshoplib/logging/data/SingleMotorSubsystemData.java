package com.chopshop166.chopshoplib.logging.data;

import com.chopshop166.chopshoplib.logging.DataWrapper;

/** A simple data object for a subsystem with a single motor. */
public class SingleMotorSubsystemData extends DataWrapper {

    /** The data object for the motor. */
    public MotorControllerData motor = new MotorControllerData(false);

    /**
     * Convenience wrapper for setting the motor's setpoint.
     * 
     * @param setpoint The setpoint for the motor.
     */
    public void setSetpoint(final double setpoint) {
        this.motor.setpoint = setpoint;
    }
}
