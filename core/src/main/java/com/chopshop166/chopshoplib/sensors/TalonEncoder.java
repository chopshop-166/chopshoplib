package com.chopshop166.chopshoplib.sensors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;

import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * An Encoder attached to the Talon SRX.
 */
public class TalonEncoder implements IEncoder {

    /** Reference to the base Talon SRX. */
    private final BaseTalon talon;
    /** The resolution (revolutions per tick). */
    private final double revPerTick;

    /**
     * Construct the encoder from the Talon.
     * 
     * @param talon      The Talon SRX to connect to.
     * @param resolution The number of ticks per encoder.
     */
    public TalonEncoder(final BaseTalon talon, final double resolution) {
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
        return talon.getSelectedSensorPosition() * revPerTick;
    }

    /**
     * Get the velocity of the encoder.
     * 
     * @return The velocity in rpm.
     */
    @Override
    public double getRate() {
        return talon.getSelectedSensorVelocity() * revPerTick;
    }

    @Override
    public void reset() {
        // No no, we don't do that here.
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Encoder");
        builder.addDoubleProperty("Speed", this::getRate, null);
        builder.addDoubleProperty("Distance", this::getDistance, null);
        builder.addDoubleProperty("Distance per Tick", () -> revPerTick, null);
    }
}