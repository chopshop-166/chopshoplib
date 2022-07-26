package com.chopshop166.chopshoplib.motors;

import com.chopshop166.chopshoplib.sensors.TalonEncoder;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/**
 * Talon that is compatible with the Smart Motor Controller.
 */
public class CSTalonBase<T extends BaseTalon & MotorController & Sendable> extends SmartMotorController {

    /** Reference to the wrapped Talon. */
    private final T wrapped;
    /** The Talon control mode. */
    private ControlMode savedControlType = ControlMode.PercentOutput;

    /**
     * Create a Talon.
     *
     * @param wrapped    The raw object to wrap.
     * @param resolution The number of ticks per revolution.
     */
    public CSTalonBase(final T wrapped, final double resolution) {
        super(wrapped, new TalonEncoder(wrapped, resolution));
        this.wrapped = wrapped;
    }

    /**
     * Get the wrapped speed controller.
     *
     * @return The raw Talon object.
     */
    public T getMotorController() {
        return wrapped;
    }

    /**
     * Set the control type.
     *
     * @param controlType The controlType to set.
     */
    @Override
    public void setControlType(final PIDControlType controlType) {
        if (controlType == PIDControlType.Position) {
            this.savedControlType = ControlMode.Position;
        } else if (controlType == PIDControlType.Velocity) {
            this.savedControlType = ControlMode.Velocity;
        }
    }

    /**
     * Set the control type to a nonstandard one.
     *
     * @param controlType The controlType to set.
     */
    public void setControlType(final ControlMode controlType) {
        this.savedControlType = controlType;
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
}