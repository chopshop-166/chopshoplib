package com.chopshop166.chopshoplib.outputs;

import com.chopshop166.chopshoplib.sensors.TalonEncoder;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Talon SRX that implements the PID speed controller interface.
 */
public class PIDTalon extends WPI_TalonSRX implements PIDSpeedController {

    /** The Talon control mode. */
    private ControlMode controlMode = ControlMode.PercentOutput;
    /** Reference to the encoder. */
    private final TalonEncoder encoder;

    /**
     * Create a Talon.
     * 
     * @param deviceNumber The device number to use.
     */
    public PIDTalon(final int deviceNumber) {
        super(deviceNumber);
        encoder = new TalonEncoder(this);
    }

    /**
     * Set the control mode of the Talon.
     * 
     * @param controlMode The mode to use.
     */
    public void setControlMode(final ControlMode controlMode) {
        this.controlMode = controlMode;
    }

    @Override
    public TalonEncoder getEncoder() {
        return encoder;
    }

    @Override
    public void setP(final double kp) {
        config_kP(0, kp);
    }

    @Override
    public void setI(final double ki) {
        config_kI(0, ki);
    }

    @Override
    public void setD(final double kd) {
        config_kD(0, kd);
    }

    @Override
    public void setF(final double kf) {
        config_kF(0, kf);
    }

    @Override
    public void setSetpoint(final double setPoint) {
        set(controlMode, setPoint);
    }
}