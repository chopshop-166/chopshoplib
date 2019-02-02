package com.chopshop166.chopshoplib.sensors;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * A wrapper for the {@link CANEncoder} provided by REV Robotics, to implement
 * WPIlib interfaces.
 */
public class SparkMaxCounter extends SendableBase implements CounterBase, PIDSource {

    private boolean isReversed;
    private double resetPoint;
    private CANEncoder encoder;
    private PIDSourceType pidSource;

    /**
     * Create a wrapper object
     * 
     * @param encoder The encoder to wrap around
     */
    public SparkMaxCounter(final CANEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Get the distance travelled
     * 
     * @return the distance in revolutions
     */
    double getDistance() {
        double position = (encoder.getPosition() - resetPoint);
        if (isReversed) {
            position *= -1;
        }
        return position;
    }

    /**
     * Get the velocity of the encoder
     * 
     * @return the velocity in rpm
     */
    double getRate() {
        double velocity = encoder.getVelocity();
        if (isReversed) {
            velocity *= -1;
        }
        return velocity;
    }

    /**
     * Get if the encoder is reversed
     * 
     * @return true if reversed, otherwise false
     */
    boolean getReverseDirection() {
        return isReversed;
    }

    /**
     * Set if the encoder is reversed
     * 
     * @param isReversed true if the encoder is reversed, otherwise false
     */
    void setReverseDirection(boolean isReversed) {
        this.isReversed = isReversed;
    }

    @Override
    public void reset() {
        resetPoint = encoder.getPosition();
    }

    @Override
    public boolean getStopped() {
        return encoder.getVelocity() == 0;
    }

    @Override
    public boolean getDirection() {
        return encoder.getVelocity() >= 0.0;
    }

    @Override
    public int get() {
        return (int) pidGet();
    }

    @Override
    public void setMaxPeriod(double maxPeriod) {
        // This operation not supported, but needed for the interface
    }

    @Override
    public double getPeriod() {
        // This operation not supported, but needed for the interface
        return 0;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
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
            return 0.0;
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Encoder");
        builder.addDoubleProperty("Speed", this::getRate, null);
        builder.addDoubleProperty("Distance", this::get, null);
        builder.addDoubleProperty("Distance per Tick", () -> 1.0, null);
    }

}