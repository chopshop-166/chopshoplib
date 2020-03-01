package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Mock speed controller that can be used to simulate a motor on a driver
 * station, with "PID".
 */
public class MockPIDSpeedController implements PIDSpeedController {

    /** The last set speed. */
    private double speed;
    /** The last set setpoint. */
    private double setpoint;
    /** Whether the speed controller is inverted. */
    private boolean isInverted;
    /** Proportional coefficient. */
    private double p;
    /** Integral coefficient. */
    private double i;
    /** Derivative coefficient. */
    private double d;

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
        builder.addDoubleProperty("Setpoint", () -> setpoint, this::setSetpoint);
        builder.addDoubleProperty("P", () -> p, this::setP);
        builder.addDoubleProperty("I", () -> i, this::setI);
        builder.addDoubleProperty("D", () -> d, this::setD);
    }

    @Override
    public void setP(final double kp) {
        p = kp;
    }

    @Override
    public void setI(final double ki) {
        i = ki;
    }

    @Override
    public void setD(final double kd) {
        d = kd;
    }

    @Override
    public void setSetpoint(final double setPoint) {
        setpoint = setPoint;
    }
}
