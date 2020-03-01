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

    /** The speed controller to run. */
    final private SmartSpeedController motor;
    /** The PID controller for calculations. */
    final private PIDController pid;
    /** The measurement source. */
    final private DoubleSupplier measurement;
    /** The watchdog to repeatedly trigger the calculation. */
    final private Watchdog dog = new Watchdog(1.0 / 50.0, this::calculate);
    /** The feedforward coefficient. */
    private double feedforward;
    /** The setpoint to move to. */
    private double setpoint;
    /** Whether the PID controller is enabled. */
    private boolean enabled = true;

    /**
     * Use a PID controller with the position of an encoder.
     * 
     * @param motor The speed controller to move.
     * @param pid   The PID controller to use for calculation.
     * @return The new PID controller.
     */
    public static SwPIDSpeedController position(final SmartSpeedController motor, final PIDController pid) {
        // Uses a lambda so that it always gets the current encoder, instead of the one
        // assigned at creation time.
        return new SwPIDSpeedController(motor, pid, () -> motor.getEncoder().getDistance());
    }

    /**
     * Use a PID controller with the position of an encoder.
     * 
     * @param <T>     A type that's a Speed Controller and also Sendable.
     * @param motor   The speed controller to move.
     * @param pid     The PID controller to use for calculation.
     * @param encoder The encoder to use for measurement.
     * @return The new PID controller.
     */
    public static <T extends Sendable & SpeedController> SwPIDSpeedController position(final T motor,
            final PIDController pid, final IEncoder encoder) {
        return position(new ModSpeedController(motor, encoder), pid);
    }

    /**
     * Use a PID controller with the velocity of an encoder.
     * 
     * @param motor The speed controller to move.
     * @param pid   The PID controller to use for calculation.
     * @return The new PID controller.
     */
    public static SwPIDSpeedController velocity(final SmartSpeedController motor, final PIDController pid) {
        // Uses a lambda so that it always gets the current encoder, instead of the one
        // assigned at creation time.
        return new SwPIDSpeedController(motor, pid, () -> motor.getEncoder().getRate());
    }

    /**
     * Use a PID controller with the velocity of an encoder.
     * 
     * @param <T>     A type that's a Speed Controller and also Sendable.
     * @param motor   The speed controller to move.
     * @param pid     The PID controller to use for calculation.
     * @param encoder The encoder to use for measurement.
     * @return The new PID controller.
     */
    public static <T extends Sendable & SpeedController> SwPIDSpeedController velocity(final T motor,
            final PIDController pid, final IEncoder encoder) {
        return velocity(new ModSpeedController(motor, encoder), pid);
    }

    /**
     * Create a PID speed controller using software PID.
     * 
     * @param motor       The speed controller to use.
     * @param pid         The PID controller for calculation.
     * @param measurement The measurement source.
     */
    public SwPIDSpeedController(final SmartSpeedController motor, final PIDController pid,
            final DoubleSupplier measurement) {
        this.motor = motor;
        this.pid = pid;
        this.measurement = measurement;
        dog.suppressTimeoutMessage(true);
        dog.enable();
    }

    /**
     * Create a PID speed controller using software PID.
     * 
     * @param <T>         A type that's a Speed Controller and also Sendable.
     * @param motor       The speed controller to use.
     * @param pid         The PID controller for calculation.
     * @param measurement The measurement source.
     */
    public <T extends Sendable & SpeedController> SwPIDSpeedController(final T motor, final PIDController pid,
            final DoubleSupplier measurement) {
        this(SmartSpeedController.wrap(motor), pid, measurement);
    }

    /**
     * Get the raw speed controller.
     * 
     * @return The speed controller.
     */
    public SmartSpeedController getMotor() {
        return motor;
    }

    /**
     * Get the PID controller.
     * 
     * @return The PID controller.
     */
    public PIDController getController() {
        return pid;
    }

    /** Enable the PID controller. */
    public void enablePID() {
        if (!enabled) {
            enabled = true;
            dog.enable();
        }
    }

    /** Disable the PID controller. */
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

    /** Calculate the PID value, and set the speed controler to the result. */
    private void calculate() {
        final double ff = feedforward * setpoint;
        final double meas = measurement.getAsDouble();
        final double calc = pid.calculate(meas);
        set(ff + calc);
        dog.reset();
    }
}
