package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * An {@link Accelerometer} that can be controlled via the dashboard.
 */
public class MockAccelerometer implements Accelerometer, Sendable {

    /** The X value. */
    private double x;
    /** The Y value. */
    private double y;
    /** The Z value. */
    private double z;
    /** The accelerometer range.. */
    private Range range;

    /**
     * Set the X value of the accelerometer.
     * 
     * @param newX The new value.
     */
    public void setX(final double newX) {
        this.x = newX;
    }

    /**
     * Set the Y value of the accelerometer.
     * 
     * @param newY The new value.
     */
    public void setY(final double newY) {
        this.y = newY;
    }

    /**
     * Set the Z value of the accelerometer.
     * 
     * @param newZ The new value.
     */
    public void setZ(final double newZ) {
        this.z = newZ;
    }

    /**
     * Get the WPILib {@link Range} for this accelerometer.
     * 
     * @return The set range.
     */
    public Range getRange() {
        return this.range;
    }

    @Override
    public void setRange(final Range range) {
        this.range = range;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Accelerometer");
        builder.addDoubleProperty("X", this::getX, this::setX);
        builder.addDoubleProperty("Y", this::getY, this::setY);
        builder.addDoubleProperty("Z", this::getZ, this::setZ);
    }
}
