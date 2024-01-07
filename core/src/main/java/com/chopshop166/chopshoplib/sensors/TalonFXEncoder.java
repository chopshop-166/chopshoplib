package com.chopshop166.chopshoplib.sensors;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.StatusSignal;

/**
 * An Encoder attached to the Talon SRX.
 */
public class TalonFXEncoder implements IEncoder {

    /** Reference to the base Talon SRX. */
    private final TalonFX talon;
    /** Signal for position. */
    private final StatusSignal<Double> positionSignal;
    /** Signal for velocity. */
    private final StatusSignal<Double> velocitySignal;

    /**
     * Construct the encoder from the Talon.
     * 
     * @param talon The Talon SRX to connect to.
     */
    public TalonFXEncoder(final TalonFX talon) {
        this.talon = talon;
        this.positionSignal = talon.getPosition();
        this.velocitySignal = talon.getVelocity();
    }

    /**
     * Get the encoder's motor.
     * 
     * @return The motor object.
     */
    public TalonFX getMotor() {
        return this.talon;
    }

    /**
     * Get the distance travelled.
     * 
     * @return The distance in revolutions.
     */
    @Override
    public double getDistance() {
        return this.positionSignal.getValueAsDouble();
    }

    /**
     * Get the velocity of the encoder.
     * 
     * @return The velocity in rpm.
     */
    @Override
    public double getRate() {
        return this.velocitySignal.getValueAsDouble();
    }

    @Override
    public void reset() {
        this.talon.setPosition(0.0);
    }
}
