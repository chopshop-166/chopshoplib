package com.chopshop166.chopshoplib.motors;

import com.chopshop166.chopshoplib.sensors.TalonFXEncoder;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

/** Convenience alias for a Talon FX. */
public class CSTalonFX extends SmartMotorController {

    /** Reference to the wrapped Talon. */
    private final TalonFX wrapped;

    /**
     * Constructor.
     *
     * @param talon The Talon object to wrap.
     */
    public CSTalonFX(final TalonFX talon) {
        super(talon, new TalonFXEncoder(talon));
        this.wrapped = talon;
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
}
