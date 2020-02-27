package com.chopshop166.chopshoplib.outputs;

import java.util.function.DoubleSupplier;

import com.chopshop166.chopshoplib.sensors.IEncoder;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Use a PID controller with a generic speed controller.
 */
public class SwPIDSpeedController implements PIDSpeedController {

    final private SmartSpeedController motor;
    final private PIDController pid;
    final private DoubleSupplier measurement;
    final private Watchdog dog = new Watchdog(1.0 / 50.0, this::calculate);
    private double feedforward;
    private double setpoint;
    private boolean enabled = true;

    public static SwPIDSpeedController position(final SmartSpeedController motor, final PIDController pid) {
        // Uses a lambda so that it always gets the current encoder, instead of the one
        // assigned at creation time.
        return new SwPIDSpeedController(motor, pid, () -> motor.getEncoder().getDistance());
    }

    public static <T extends Sendable & SpeedController> SwPIDSpeedController position(final T motor,
            final PIDController pid, final IEncoder encoder) {
        return position(new ModSpeedController(motor, encoder), pid);
    }

    public static SwPIDSpeedController velocity(final SmartSpeedController motor, final PIDController pid) {
        // Uses a lambda so that it always gets the current encoder, instead of the one
        // assigned at creation time.
        return new SwPIDSpeedController(motor, pid, () -> motor.getEncoder().getRate());
    }

    public static <T extends Sendable & SpeedController> SwPIDSpeedController velocity(final T motor,
            final PIDController pid, final IEncoder encoder) {
        return velocity(new ModSpeedController(motor, encoder), pid);
    }

    public SwPIDSpeedController(final SmartSpeedController motor, final PIDController pid,
            final DoubleSupplier measurement) {
        this.motor = motor;
        this.pid = pid;
        this.measurement = measurement;
        dog.suppressTimeoutMessage(true);
        dog.enable();
    }

    public <T extends Sendable & SpeedController> SwPIDSpeedController(final T motor, final PIDController pid,
            final DoubleSupplier measurement) {
        this(SmartSpeedController.wrap(motor), pid, measurement);
    }

    public SmartSpeedController getMotor() {
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
        pid.setI(ki);
    }

    @Override
    public void setD(final double kd) {
        pid.setD(kd);
    }

    @Override
    public void setF(final double kf) {
        feedforward = kf;
    }

    @Override
    public void setSetpoint(final double setPoint) {
        pid.setSetpoint(setPoint);
        this.setpoint = setPoint;
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

    @Override
    public IEncoder getEncoder() {
        return motor.getEncoder();
    }

    private void calculate() {
        final double ff = feedforward * setpoint;
        final double meas = measurement.getAsDouble();
        final double calc = pid.calculate(meas);
        set(ff + calc);
        dog.reset();
    }
}
