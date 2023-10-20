package com.chopshop166.chopshoplib.sensors;

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
}
