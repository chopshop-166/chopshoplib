package com.chopshop166.chopshoplib.sensors;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * A wrapper for the {@link CANEncoder} provided by REV Robotics, to implement
 * WPIlib interfaces.
 */
public class SparkMaxEncoder implements IEncoder, Sendable {

    private boolean isReversed;
    private double resetPoint;
    private double scaleFactor;
    private final CANEncoder encoder;

    /**
     * Create a wrapper object.
     * 
     * @param encoder The encoder to wrap around.
     */
    public SparkMaxEncoder(final CANEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Create a wrapper object.
     * 
     * @param max The Spark MAX to get the encoder for.
     */
    public SparkMaxEncoder(final CANSparkMax max) {
        this.encoder = max.getEncoder();
    }

    /**
     * Get the distance travelled.
     * 
     * @return The distance in revolutions.
     */
    @Override
    public double getDistance() {
        double position = encoder.getPosition() - resetPoint;
        if (isReversed) {
            position *= -1;
        }
        return position * scaleFactor;
    }

    /**
     * Get the velocity of the encoder.
     * 
     * @return The velocity in rpm.
     */
    @Override
    public double getRate() {
        double velocity = encoder.getVelocity();
        if (isReversed) {
            velocity *= -1;
        }
        return velocity * scaleFactor;
    }

    /**
     * Get if the encoder is reversed.
     * 
     * @return true if reversed, otherwise false.
     */
    public boolean isReverseDirection() {
        return isReversed;
    }

    /**
     * Set if the encoder is reversed.
     * 
     * @param isReversed true if the encoder is reversed, otherwise false.
     */
    public void setReverseDirection(final boolean isReversed) {
        this.isReversed = isReversed;
    }

    @Override
    public void reset() {
        resetPoint = encoder.getPosition();
    }

    @Override
    public boolean isStopped() {
        return encoder.getVelocity() == 0;
    }

    @Override
    public boolean isMovingForward() {
        return encoder.getVelocity() >= 0.0;
    }

    /**
     * Sets the scale factor used to convert encoder values to useful units.
     * 
     * @param scaleFactor The scaleFactor to set.
     */
    public void setScaleFactor(final double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    /**
     * Return the scale factor used to convert the encoder values to useful units.
     * 
     * @return The scale factor.
     */
    public double getScaleFactor() {
        return scaleFactor;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Encoder");
        builder.addDoubleProperty("Speed", this::getRate, null);
        builder.addDoubleProperty("Distance", this::getDistance, null);
        builder.addDoubleProperty("Distance per Tick", this::getScaleFactor, this::setScaleFactor);
    }
}