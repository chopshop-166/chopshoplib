package com.chopshop166.chopshoplib.sensors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;

/**
 * An Encoder attached to the Talon SRX.
 */
public class TalonSRXEncoder implements IEncoder {

    /** Reference to the base Talon SRX. */
    private final BaseTalon talon;
    /** The resolution (revolutions per tick). */
    private final double revPerTick;

    /**
     * Construct the encoder from the Talon.
     * 
     * @param talon The Talon SRX to connect to.
     * @param resolution The number of ticks per encoder.
     */
    public TalonSRXEncoder(final BaseTalon talon, final double resolution) {
        this.talon = talon;
        this.revPerTick = 1.0 / resolution;
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    }

    /**
     * Get the distance travelled.
     * 
     * @return The distance in revolutions.
     */
    @Override
    public double getDistance() {
        return this.talon.getSelectedSensorPosition() * this.revPerTick;
    }

    /**
     * Get the velocity of the encoder.
     * 
     * @return The velocity in rpm.
     */
    @Override
    public double getRate() {
        return this.talon.getSelectedSensorVelocity() * this.revPerTick;
    }

    @Override
    public void reset() {
        this.talon.setSelectedSensorPosition(0.0);
    }
}
