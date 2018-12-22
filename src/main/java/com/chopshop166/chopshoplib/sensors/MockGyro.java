package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * A {@link Gyro} that can be controlled via the dashboard.
 */
public class MockGyro extends SendableBase implements Gyro {

    private double angle;

    @Override
    public double getAngle() {
        return angle;
    }

    public void setAngle(final double angle) {
        this.angle = angle;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Gyro");
        builder.addDoubleProperty("Value", this::getAngle, this::setAngle);
    }

    @Override
    public void calibrate() {
        // Do nothing
    }

    @Override
    public void reset() {
        angle = 0.0;
    }

    @Override
    public double getRate() {
        return 0;
    }
}