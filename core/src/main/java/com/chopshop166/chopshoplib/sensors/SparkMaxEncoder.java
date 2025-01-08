package com.chopshop166.chopshoplib.sensors;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

/**
 * A wrapper for the {@link RelativeEncoder} provided by REV Robotics, to implement WPIlib
 * interfaces.
 */
public class SparkMaxEncoder implements IEncoder {

    /** The motor that the wrapped encoder is connected to */
    private final SparkMax motor;
    /** The wrapped encoder. */
    private final RelativeEncoder encoder;

    /**
     * Create a wrapper object.
     * 
     * @param motor The Spark MAX get the encoder for.
     */
    public SparkMaxEncoder(final SparkMax motor) {
        this.motor = motor;
        this.encoder = motor.getEncoder();
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
     * @param positionScaleFactor The scale factor to set for position.
     * @param velocityScaleFactor The scale factor to set for velocity.
     */
    public void setScaleFactors(final double positionScaleFactor,
            final double velocityScaleFactor) {
        final var config = new SparkMaxConfig();
        config.encoder.positionConversionFactor(positionScaleFactor);
        config.encoder.velocityConversionFactor(velocityScaleFactor);
        this.motor.configure(config, ResetMode.kNoResetSafeParameters,
                PersistMode.kPersistParameters);
    }

    /**
     * Return the scale factor used to convert the encoder values to useful units.
     * 
     * @return The scale factor.
     */
    public double getPositionScaleFactor() {
        return this.motor.configAccessor.encoder.getPositionConversionFactor();
    }

    /**
     * Return the scale factor used to convert the encoder values to useful units.
     * 
     * @return The scale factor.
     */
    public double getVelocityScaleFactor() {
        return this.motor.configAccessor.encoder.getVelocityConversionFactor();
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
