package com.chopshop166.chopshoplib.outputs;

import java.util.function.DoubleSupplier;

import com.chopshop166.chopshoplib.sensors.IEncoder;
import com.chopshop166.chopshoplib.sensors.MockEncoder;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.controller.PIDController;

/**
 * Use a PID controller with a generic speed controller.
 */
public class SwPIDSpeedController extends SmartMotorController {

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
    private boolean pidEnabled = true;

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
        return new SwPIDSpeedController(motor, encoder, pid, encoder::getDistance);
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
        return new SwPIDSpeedController(motor, encoder, pid, encoder::getRate);
    }

    /**
     * Create a PID speed controller using software PID.
     * 
     * @param <T>         The unwrapped type of a motor controller
     * @param motor       The motor controller to use.
     * @param pid         The PID controller for calculation.
     * @param measurement The measurement source.
     * @param modifiers   Any output modifiers.
     */
    public <T extends Sendable & SpeedController> SwPIDSpeedController(final T motor, final PIDController pid,
            final DoubleSupplier measurement, final Modifier... modifiers) {
        this(motor, new MockEncoder(), pid, measurement, modifiers);
    }

    /**
     * Create a PID speed controller using software PID.
     * 
     * @param <T>         The unwrapped type of a motor controller
     * @param motor       The motor controller to use.
     * @param encoder     The encoder to use.
     * @param pid         The PID controller for calculation.
     * @param measurement The measurement source.
     * @param modifiers   Any output modifiers.
     */
    public <T extends Sendable & SpeedController> SwPIDSpeedController(final T motor, final IEncoder encoder,
            final PIDController pid, final DoubleSupplier measurement, final Modifier... modifiers) {
        super(motor, encoder, modifiers);
        this.measurement = measurement;
        this.pid = pid;
        dog.suppressTimeoutMessage(true);
        dog.enable();
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
        if (!pidEnabled) {
            pidEnabled = true;
            dog.enable();
        }
    }

    /** Disable the PID controller. */
    public void disablePID() {
        pidEnabled = false;
        dog.disable();
    }

    @Override
    public void setSetpoint(final double setPoint) {
        pid.setSetpoint(setPoint);
        this.setpoint = setPoint;
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
