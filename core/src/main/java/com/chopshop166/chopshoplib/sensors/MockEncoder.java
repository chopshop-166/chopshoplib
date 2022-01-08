package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * An {@link IEncoder} that can be controlled via the dashboard.
 */
public class MockEncoder implements IEncoder {

    /** The distance rotated. */
    private double distance;
    /** The rate of rotation. */
    private double rate;

    @Override
    public void reset() {
        this.distance = 0;
    }

    @Override
    public double getDistance() {
        return this.distance;
    }

    /**
     * Set the current distance the "encoder" has traveled.
     * 
     * @param distance the "encoder" has traveled
     */
    public void setDistance(final double distance) {
        this.distance = distance;
    }

    @Override
    public double getRate() {
        return this.rate;
    }

    /**
     * Sets the current rate of the "encoder" movement.
     * 
     * @param rate the "encoder" is moving at
     */
    public void setRate(final double rate) {
        this.rate = rate;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Quadrature Encoder");
        builder.addDoubleProperty("Speed", this::getRate, this::setRate);
        builder.addDoubleProperty("Distance", this::getDistance, this::setDistance);
        builder.addDoubleProperty("Distance per Tick", () -> 0, null);
    }
}