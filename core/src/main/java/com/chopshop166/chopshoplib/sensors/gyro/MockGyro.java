package com.chopshop166.chopshoplib.sensors.gyro;

import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * A mock Gyro implementation.
 */
public class MockGyro implements SmartGyro {

    /** The angle it's rotated to. */
    private double angle;
    /** The rate of rotation. */
    private double rate;

    /**
     * Set the angle.
     * 
     * @param angle The new angle.
     */
    public void setAngle(final double angle) {
        this.angle = angle;
    }

    /**
     * Set the rate.
     * 
     * @param rate The new rate.
     */
    public void setRate(final double rate) {
        this.rate = rate;
    }

    @Override
    public double getAngle() {
        return angle;
    }

    @Override
    public double getRate() {
        return rate;
    }

    @Override
    public void reset() {
        angle = 0;
    }

    @Override
    public void calibrate() {
        // Nothing to calibrate
    }

    @Override
    public void close() {
        // Nothing to close
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Gyro");
        builder.addDoubleProperty("Value", this::getAngle, this::setAngle);
        builder.addDoubleProperty("Rate", this::getRate, this::setRate);
    }

}