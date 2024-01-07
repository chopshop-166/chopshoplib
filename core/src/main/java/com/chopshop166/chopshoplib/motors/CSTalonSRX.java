package com.chopshop166.chopshoplib.motors;

import java.util.ArrayList;
import java.util.List;
import com.chopshop166.chopshoplib.sensors.TalonSRXEncoder;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/** Convenience alias for a Talon SRX. */
public class CSTalonSRX extends SmartMotorController {

    /** Reference to the wrapped Talon. */
    private final WPI_TalonSRX wrapped;
    /** The Talon control mode. */
    private ControlMode savedControlType = ControlMode.PercentOutput;
    /** List of Configurations that we can switch between. */
    final private List<TalonSRXConfiguration> config = new ArrayList<>(4);

    /**
     * Constructor.
     *
     * @param talon The Talon object to wrap.
     */
    public CSTalonSRX(final WPI_TalonSRX talon) {
        super(talon, new TalonSRXEncoder(talon, 4096));
        this.wrapped = talon;
    }

    /**
     * Convenience constructor.
     *
     * @param deviceNumber The device number to construct with.
     */
    public CSTalonSRX(final int deviceNumber) {
        this(new WPI_TalonSRX(deviceNumber));
    }

    /**
     * Get the wrapped speed controller.
     *
     * @return The raw Talon object.
     */
    public WPI_TalonSRX getMotorController() {
        return this.wrapped;
    }

    /**
     * Add the default configuration to the list of configurations and set it in the
     * TalonSRX.
     *
     * @param config Configuration to add to the list of stored configs.
     */
    public void addDefaultConfiguration(final TalonSRXConfiguration config) {
        this.config.add(config);
        this.getMotorController().configAllSettings(config);
    }

    /**
     * Add a configuration to the list of configurations we can swap to.
     *
     * @param config Configuration to add to the list of stored configs.
     */
    public void addConfiguration(final TalonSRXConfiguration config) {
        this.config.add(config);
    }

    @Override
    public void setPidSlot(final int slotId) {
        this.getMotorController().configAllSettings(this.config.get(slotId));
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
        return this.savedControlType;
    }

    @Override
    public TalonSRXEncoder getEncoder() {
        // This cast is safe because we're the ones setting it in the first place.
        return (TalonSRXEncoder) super.getEncoder();
    }

    @Override
    public void setSetpoint(final double setPoint) {
        this.wrapped.set(this.savedControlType, setPoint);
    }

    @Override
    public double[] getCurrentAmps() {
        return new double[] { this.wrapped.getSupplyCurrent() };
    }

    @Override
    public double[] getTemperatureC() {
        return new double[] { this.wrapped.getTemperature() };
    }

}
