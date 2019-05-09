package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class MockGyro extends GyroBase implements PIDGyro {

    private double angle;
    private double rate;

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setRate(double rate) {
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
    public void free() {
        // Nothing to free (deprecated)
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Gyro");
        builder.addDoubleProperty("Value", this::getAngle, this::setAngle);
        builder.addDoubleProperty("Rate", this::getRate, this::setRate);
    }

}