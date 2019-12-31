package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Mock speed controller that can be used to simulate a motor on a driver
 * station.
 */
public class MockSpeedController implements SendableSpeedController {

    private double speed;
    private boolean isInverted;

    @Override
    public void pidWrite(final double output) {
        set(output);
    }

    @Override
    public void set(final double speed) {
        this.speed = isInverted ? -speed : speed;
    }

    @Override
    public double get() {
        return speed;
    }

    @Override
    public void setInverted(final boolean isInverted) {
        this.isInverted = isInverted;
    }

    @Override
    public boolean getInverted() {
        return isInverted;
    }

    @Override
    public void disable() {
        this.speed = 0.0;
    }

    @Override
    public void stopMotor() {
        this.speed = 0.0;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Speed Controller");
        builder.setActuator(true);
        builder.setSafeState(this::disable);
        builder.addDoubleProperty("Value", this::get, this::set);
    }
}
