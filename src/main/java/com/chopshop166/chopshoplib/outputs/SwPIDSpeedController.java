package com.chopshop166.chopshoplib.outputs;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Mock speed controller that can be used to simulate a motor on a driver
 * station, with "PID".
 */
public class SwPIDSpeedController implements PIDSpeedController {

    final private SendableSpeedController motor;
    final private PIDController pid;
    final private DoubleSupplier measurement;
    final private Watchdog dog = new Watchdog(1.0 / 50.0, this::calculate);
    private boolean enabled = true;

    public SwPIDSpeedController(final SendableSpeedController motor, final PIDController pid,
            final DoubleSupplier measurement) {
        this.motor = motor;
        this.pid = pid;
        this.measurement = measurement;
        dog.suppressTimeoutMessage(true);
        dog.enable();
    }

    public <T extends Sendable & SpeedController> SwPIDSpeedController(final T motor, final PIDController pid,
            final DoubleSupplier measurement) {
        this(SendableSpeedController.wrap(motor), pid, measurement);
    }

    public SendableSpeedController getMotor() {
        return motor;
    }

    public PIDController getController() {
        return pid;
    }

    public void enablePID() {
        if (!enabled) {
            enabled = true;
            dog.enable();
        }
    }

    public void disablePID() {
        enabled = false;
        dog.disable();
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        motor.initSendable(builder);
    }

    @Override
    public void setP(final double kp) {
        pid.setP(kp);
    }

    @Override
    public void setI(final double ki) {
        pid.setP(ki);
    }

    @Override
    public void setD(final double kd) {
        pid.setP(kd);
    }

    @Override
    public void setSetpoint(final double setPoint) {
        pid.setSetpoint(setPoint);
    }

    @Override
    public void set(final double speed) {
        motor.set(speed);
    }

    @Override
    public double get() {
        return motor.get();
    }

    @Override
    public void setInverted(final boolean isInverted) {
        motor.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return motor.getInverted();
    }

    @Override
    public void disable() {
        motor.disable();
    }

    @Override
    public void stopMotor() {
        motor.stopMotor();
    }

    @Override
    public void pidWrite(final double output) {
        motor.pidWrite(output);
    }

    private void calculate() {
        set(pid.calculate(measurement.getAsDouble()));
        dog.reset();
    }
}
