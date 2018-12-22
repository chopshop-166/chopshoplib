package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * An {@link Accelerometer} that can be controlled via the dashboard.
 */
public class MockAccelerometer extends SendableBase implements Accelerometer {

    private double x;
    private double y;
    private double z;
    private Range range;

    /**
     * Set the X value of the accelerometer.
     * 
     * @param newX The new value.
     */
    public void setX(final double newX) {
        x = newX;
    }

    /**
     * Set the Y value of the accelerometer.
     * 
     * @param newY The new value.
     */
    public void setY(final double newY) {
        y = newY;
    }

    /**
     * Set the Z value of the accelerometer.
     * 
     * @param newZ The new value.
     */
    public void setZ(final double newZ) {
        z = newZ;
    }

    /**
     * Get the WPILib {@link Range} for this accelerometer.
     * 
     * @return The set range.
     */
    public Range getRange() {
        return range;
    }

    @Override
    public void setRange(final Range range) {
        this.range = range;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Accelerometer");
        builder.addDoubleProperty("X", this::getX, this::setX);
        builder.addDoubleProperty("Y", this::getY, this::setY);
        builder.addDoubleProperty("Z", this::getZ, this::setZ);
    }
}