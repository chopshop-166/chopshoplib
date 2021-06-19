package com.chopshop166.chopshoplib.outputs;

import com.chopshop166.chopshoplib.sensors.TalonEncoder;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Talon SRX that is compatible with the Smart Motor Controller.
 */
public class PIDTalon extends SmartMotorController {

    /** Reference to the wrapped Talon. */
    private final WPI_TalonSRX wrapped;
    /** The Talon control mode. */
    private ControlMode savedControlType = ControlMode.PercentOutput;

    /**
     * Create a Talon.
     * 
     * @param wrapped The raw object to wrap.
     */
    public PIDTalon(final WPI_TalonSRX wrapped) {
        super(new MockSpeedController(), new TalonEncoder(wrapped));
        this.wrapped = wrapped;
    }

    /**
     * Get the wrapped speed controller.
     * 
     * @return The raw Talon SRX object.
     */
    public WPI_TalonSRX getMotorController() {
        return wrapped;
    }

    /**
     * Set the control mode of the Talon.
     * 
     * @param controlMode The mode to use.
     */
    public void setControlType(final ControlMode controlMode) {
        this.savedControlType = controlMode;
    }

    /**
     * Get the control mode of the Talon.
     * 
     * @return The control mode.
     */
    public ControlMode getControlType() {
        return savedControlType;
    }

    @Override
    public TalonEncoder getEncoder() {
        // This cast is safe because we're the ones setting it in the first place.
        return (TalonEncoder) super.getEncoder();
    }

    @Override
    public void setSetpoint(final double setPoint) {
        wrapped.set(savedControlType, setPoint);
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        wrapped.initSendable(builder);
    }
}