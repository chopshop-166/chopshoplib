package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * A {@link Potentiometer} that can be controlled via the dashboard.
 */
public class MockPIDSource implements Sendable {

    private double value;

    public double get() {
        return value;
    }

    public void set(final double value) {
        this.value = value;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Potentiometer");
        builder.addDoubleProperty("Value", this::get, this::set);
    }
}