package com.chopshop166.chopshoplib.sensors;

import com.revrobotics.CANSparkBase;
import com.revrobotics.RelativeEncoder;

/**
 * A wrapper for the {@link RelativeEncoder} provided by REV Robotics, to implement WPIlib
 * interfaces.
 */
public class SparkEncoder implements IEncoder {

    /** The wrapped encoder. */
    private final RelativeEncoder encoder;

    /**
     * Create a wrapper object.
     * 
     * @param encoder The encoder to wrap around.
     */
    public SparkEncoder(final RelativeEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Create a wrapper object.
     * 
     * @param max The Spark MAX to get the encoder for.
     */
    public SparkEncoder(final CANSparkBase max) {
        this.encoder = max.getEncoder();
    }

    /**
     * Get the wrapped encoder.
     * 
     * @return The wrapped encoder.
     */
    public RelativeEncoder getRaw() {
        return this.encoder;
    }

    /**
     * Sets the scale factor used to convert encoder values to useful units.
     * 
     * @param scaleFactor The scaleFactor to set.
     */
    public void setPositionScaleFactor(final double scaleFactor) {
        this.encoder.setPositionConversionFactor(scaleFactor);
    }

    /**
     * Return the scale factor used to convert the encoder values to useful units.
     * 
     * @return The scale factor.
     */
    public double getPositionScaleFactor() {
        return this.encoder.getPositionConversionFactor();
    }

    /**
     * Sets the scale factor used to convert encoder values to useful units.
     * 
     * @param scaleFactor The scaleFactor to set.
     */
    public void setVelocityScaleFactor(final double scaleFactor) {
        this.encoder.setVelocityConversionFactor(scaleFactor);
    }

    /**
     * Return the scale factor used to convert the encoder values to useful units.
     * 
     * @return The scale factor.
     */
    public double getVelocityScaleFactor() {
        return this.encoder.getVelocityConversionFactor();
    }

    /**
     * Get the distance travelled.
     * 
     * @return The distance in revolutions.
     */
    @Override
    public double getDistance() {
        return this.encoder.getPosition();
    }

    /**
     * Get the velocity of the encoder.
     * 
     * @return The velocity in rpm.
     */
    @Override
    public double getRate() {
        return this.encoder.getVelocity();
    }

    @Override
    public void reset() {
        this.encoder.setPosition(0.0);
    }

    @Override
    public boolean isStopped() {
        return this.encoder.getVelocity() == 0;
    }

    @Override
    public boolean isMovingForward() {
        return this.encoder.getVelocity() >= 0.0;
    }
}
