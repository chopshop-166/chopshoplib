package com.chopshop166.chopshoplib.motors;

import com.chopshop166.chopshoplib.sensors.TalonFXEncoder;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

/** Convenience alias for a Talon FX. */
public class CSTalonFX extends SmartMotorController {

    /** Reference to the wrapped Talon. */
    private final TalonFX wrapped;
    /** The configuration of the talon. */
    private final TalonFXConfiguration config = new TalonFXConfiguration();

    /**
     * Constructor.
     *
     * @param talon The Talon object to wrap.
     */
    public CSTalonFX(final TalonFX talon) {
        super(new MockMotorController(), new TalonFXEncoder(talon));
        this.wrapped = talon;
        wrapped.getConfigurator().refresh(config);
    }

    /**
     * Convenience constructor.
     *
     * @param deviceNumber The device number to construct with.
     */
    public CSTalonFX(final int deviceNumber) {
        this(new TalonFX(deviceNumber));
    }

    /**
     * Get the wrapped speed controller.
     *
     * @return The raw Talon object.
     */
    public TalonFX getMotorController() {
        return this.wrapped;
    }

    @Override
    public void set(final double speed) {
        this.wrapped.setControl(new DutyCycleOut(speed));
    }

    @Override
    public double get() {
        return this.wrapped.get();
    }

    @Override
    public boolean getInverted() {
        return config.MotorOutput.Inverted == InvertedValue.Clockwise_Positive;
    }

    @Override
    public void setInverted(boolean isInverted) {
        config.MotorOutput.Inverted = isInverted ? InvertedValue.Clockwise_Positive
                : InvertedValue.CounterClockwise_Positive;
        wrapped.getConfigurator().apply(config);
    }

    @Override
    public double[] getVoltage() {
        return new double[] {this.wrapped.getMotorVoltage().getValueAsDouble()};
    }

    @Override
    public int[] getFaultData() {
        return new int[] {this.wrapped.getFaultField().getValue()};
    }

    @Override
    public int[] getStickyFaultData() {
        return new int[] {this.wrapped.getStickyFaultField().getValue()};
    }

    @Override
    public String getMotorControllerType() {
        return "Talon FX";
    }

    @Override
    public TalonFXEncoder getEncoder() {
        return (TalonFXEncoder) super.getEncoder();
    }
}
