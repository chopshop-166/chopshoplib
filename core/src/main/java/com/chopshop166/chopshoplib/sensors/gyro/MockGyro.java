package com.chopshop166.chopshoplib.sensors.gyro;

/**
 * A mock Gyro implementation.
 */
public class MockGyro implements SmartGyro {

    /** The angle it's rotated to. */
    private double angle;
    /** The rate of rotation. */
    private double rate;

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
        return this.angle;
    }

    /**
     * Set the angle.
     *
     * @param angle The new angle.
     */
    @Override
    public void setAngle(final double angle) {
        this.angle = angle;
    }

    @Override
    public double getRate() {
        return this.rate;
    }

    @Override
    public void reset() {
        this.angle = 0;
    }

}
