package com.chopshop166.chopshoplib.sensors;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * A wrapper for the {@link CANEncoder} provided by REV Robotics, to implement
 * WPIlib interfaces.
 */
public class SparkMaxEncoder extends SendableBase implements IEncoder {

    private boolean isReversed;
    private double resetPoint;
    private double scaleFactor;
    private final CANEncoder encoder;
    private PIDSourceType pidSource = PIDSourceType.kDisplacement;

    /**
     * Create a wrapper object.
     * 
     * @param encoder The encoder to wrap around
     */
    public SparkMaxEncoder(final CANEncoder encoder) {
        super();
        this.encoder = encoder;
    }

    /**
     * Get the distance travelled.
     * 
     * @return the distance in revolutions
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
     * @return the velocity in rpm
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
     * @return true if reversed, otherwise false
     */
    public boolean isReverseDirection() {
        return isReversed;
    }

    /**
     * Set if the encoder is reversed.
     * 
     * @param isReversed true if the encoder is reversed, otherwise false
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

    @Override
    public void setPIDSourceType(final PIDSourceType pidSource) {
        this.pidSource = pidSource;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return pidSource;
    }

    @Override
    public double pidGet() {
        switch (pidSource) {
        case kDisplacement:
            return getDistance();
        case kRate:
            return getRate();
        default:
            return Double.NaN;
        }
    }

    /**
     * Sets the scale factor used to convert encoder values to useful units.
     * 
     * @param scaleFactor the scaleFactor to set
     */
    public void setScaleFactor(final double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    /**
     * Return the scale factor used to convert the encoder values to useful units.
     * 
     * @return the scale factor
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