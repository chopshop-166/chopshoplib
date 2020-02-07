package com.chopshop166.chopshoplib.outputs;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

/**
 * PIDSpeedController
 * <p>
 * Derives PIDSpeedController to allow for use on a SparkMax Speed Controller It
 * will act as a normal SparkMax with encoders, but will also be able to use
 * PID.
 * 
 * @author Andrew Martin
 * @since 2020-02-7
 */

public class PIDSparkMax implements PIDSpeedController {
    private final CANPIDController sparkPID;

    public PIDSparkMax(final CANSparkMax max) {
        sparkPID = new CANPIDController(max);
    }

    @Override
    public void setP(final double kp) {
        sparkPID.setP(kp);
    }

    @Override
    public void setI(final double ki) {
        sparkPID.setI(ki);
    }

    @Override
    public void setD(final double kd) {
        sparkPID.setD(kd);
    }

    @Override
    public void setSetpoint(final double setPoint) {
        sparkPID.setReference(setPoint, ControlType.kVelocity);
    }

}