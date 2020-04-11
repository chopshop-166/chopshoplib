package com.chopshop166.chopshoplib.sensors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * An Encoder attached to the Talon SRX.
 */
public class TalonEncoder implements IEncoder {

    /** Reference to the base Talon SRX. */
    private final TalonSRX talon;

    /**
     * Construct the encoder from the Talon.
     * 
     * @param talon The Talon SRX to connect to.
     */
    public TalonEncoder(final TalonSRX talon) {
        this.talon = talon;
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    }

    /**
     * Get the distance travelled.
     * 
     * @return The distance in revolutions.
     */
    @Override
    public double getDistance() {
        return talon.getSelectedSensorPosition();
    }

    /**
     * Get the velocity of the encoder.
     * 
     * @return The velocity in rpm.
     */
    @Override
    public double getRate() {
        return talon.getSelectedSensorVelocity();
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
        builder.addDoubleProperty("Distance per Tick", () -> 0, null);
    }
}