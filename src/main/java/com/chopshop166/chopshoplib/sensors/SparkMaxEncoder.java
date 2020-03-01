package com.chopshop166.chopshoplib.sensors;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * A wrapper for the {@link CANEncoder} provided by REV Robotics, to implement
 * WPIlib interfaces.
 */
public class SparkMaxEncoder implements IEncoder {

    /** The wrapped encoder. */
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
     * Get the wrapped encoder.
     * 
     * @return The wrapped encoder.
     */
    public CANEncoder getRaw() {
        return encoder;
    }

    /**
     * Sets the scale factor used to convert encoder values to useful units.
     * 
     * @param scaleFactor The scaleFactor to set.
     */
    public void setPositionScaleFactor(final double scaleFactor) {
        encoder.setPositionConversionFactor(scaleFactor);
    }

    /**
     * Return the scale factor used to convert the encoder values to useful units.
     * 
     * @return The scale factor.
     */
    public double getPositionScaleFactor() {
        return encoder.getPositionConversionFactor();
    }

    /**
     * Sets the scale factor used to convert encoder values to useful units.
     * 
     * @param scaleFactor The scaleFactor to set.
     */
    public void setVelocityScaleFactor(final double scaleFactor) {
        encoder.setVelocityConversionFactor(scaleFactor);
    }

    /**
     * Return the scale factor used to convert the encoder values to useful units.
     * 
     * @return The scale factor.
     */
    public double getVelocityScaleFactor() {
        return encoder.getVelocityConversionFactor();
    }

    /**
     * Get the distance travelled.
     * 
     * @return The distance in revolutions.
     */
    @Override
    public double getDistance() {
        return encoder.getPosition();
    }

    /**
     * Get the velocity of the encoder.
     * 
     * @return The velocity in rpm.
     */
    @Override
    public double getRate() {
        return encoder.getVelocity();
    }

    /**
     * Get if the encoder is reversed.
     * 
     * @return true if reversed, otherwise false.
     */
    public boolean isReverseDirection() {
        return encoder.getInverted();
    }

    /**
     * Set if the encoder is reversed.
     * 
     * @param isReversed true if the encoder is reversed, otherwise false.
     */
    public void setReverseDirection(final boolean isReversed) {
        encoder.setInverted(isReversed);
    }

    @Override
    public void reset() {
        encoder.setPosition(0.0);
    }

    @Override
    public boolean isStopped() {
        return encoder.getVelocity() == 0;
    }

    @Override
    public boolean isMovingForward() {
        return encoder.getVelocity() >= 0.0;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Encoder");
        builder.addDoubleProperty("Speed", this::getRate, null);
        builder.addDoubleProperty("Distance", this::getDistance, null);
        builder.addDoubleProperty("Distance per Tick", this::getPositionScaleFactor, this::setPositionScaleFactor);
    }
}