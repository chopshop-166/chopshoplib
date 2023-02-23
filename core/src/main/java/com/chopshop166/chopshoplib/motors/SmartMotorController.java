package com.chopshop166.chopshoplib.motors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;
import com.chopshop166.chopshoplib.motors.validators.CurrentValidator;
import com.chopshop166.chopshoplib.motors.validators.EncoderValidator;
import com.chopshop166.chopshoplib.motors.validators.MotorValidator;
import com.chopshop166.chopshoplib.sensors.IEncoder;
import com.chopshop166.chopshoplib.sensors.MockEncoder;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/**
 * A MotorController with features attached.
 *
 * This is used in a few select scenarios, when we want to keep the exact type of an object general,
 * but at the same time want to access it as one of two disconnected interfaces.
 */
public class SmartMotorController implements Sendable, MotorController {

    /** The wrapped motor controller, as a sendable object. */
    private final Sendable sendable;
    /** The wrapped motor controller. */
    private final MotorController wrapped;
    /** An encoder, if one is attached and supplied. */
    private final IEncoder encoder;
    /** Validators. */
    private final List<MotorValidator> validators = new ArrayList<>();

    /** Construct with mocks for everything */
    public SmartMotorController() {
        this(new MockMotorController(), new MockEncoder());
    }

    /**
     * Wrap a motor controller.
     *
     * @param <T> The base type to wrap
     * @param wrapped The wrapped motor controller.
     */
    public <T extends Sendable & MotorController> SmartMotorController(final T wrapped) {
        this(wrapped, new MockEncoder());
    }

    /**
     * Wrap a motor controller with an encoder.
     *
     * @param <T> The base type to wrap
     * @param wrapped The wrapped motor controller.
     * @param encoder The encoder attached to the motor controller.
     */
    public <T extends Sendable & MotorController> SmartMotorController(final T wrapped,
            final IEncoder encoder) {
        this.sendable = wrapped;
        this.wrapped = wrapped;
        this.encoder = encoder;
    }

    /**
     * Add smart motor controller group class Get the encoder attached to the robot.
     *
     * @return An encoder, or a mock if none is attached.
     */
    public IEncoder getEncoder() {
        return this.encoder;
    }

    /**
     * Set the setpoint.
     *
     * @param setPoint The new setpoint.
     */
    public void setSetpoint(final double setPoint) {
        // Do nothing for this class
    }

    /**
     * Change what set of PID parameters are used.
     *
     * @param slotId The id of the PID parameters to use.
     */
    public void setPidSlot(final int slotId) {
        // Do nothing for this class
    }

    /**
     * Set the control type.
     *
     * @param controlType The controlType to set.
     */
    public void setControlType(final PIDControlType controlType) {
        // Do nothing for this class
    }

    /**
     * Verify that all validators pass.
     * 
     * Use this to test for things like current.
     * 
     * @return Whether all validators pass.
     */
    public boolean validate() {
        return this.validators.stream().allMatch(BooleanSupplier::getAsBoolean);
    }

    /**
     * Test if any validators failed.
     * 
     * @return Whether any validators failed.
     */
    public boolean errored() {
        return !this.validate();
    }

    /**
     * Add a validator.
     * 
     * @param validator The validator to test for.
     */
    public void addValidator(final MotorValidator validator) {
        this.validators.add(validator);
    }

    /**
     * Validate whether the encoder rate is above a certain threshold
     * 
     * @param rateThreshold the threshold to validate
     */
    public void validateEncoderRate(final double rateThreshold) {
        this.addValidator(new EncoderValidator(this.encoder::getRate, rateThreshold));
    }

    /**
     * Add a validator to make sure that the current is below a provided limit.
     * 
     * @param limit The maximum current to allow.
     * @param filterCutoff The time constant of the IIR filter.
     */
    public void validateCurrent(final double limit, final double filterCutoff) {

        this.addValidator(new CurrentValidator(
                () -> Arrays.stream(this.getCurrentAmps()).reduce(Double::sum).orElse(0.0), limit,
                filterCutoff));

    }

    /**
     * Get an array of all currents associated with the motor controller.
     * 
     * @return An array in amps.
     */
    public double[] getCurrentAmps() {
        return new double[] {0};
    }

    /**
     * Get an array of all temperatures associated with the motor controller.
     * 
     * @return An array in degrees celsius.
     */
    public double[] getTemperatureC() {
        return new double[] {0};
    }

    @Override
    public void set(final double speed) {
        this.wrapped.set(speed);
    }

    @Override
    public double get() {
        return this.wrapped.get();
    }

    /**
     * Warning: Do not use in a subsystem.
     *
     * This is intended for configuration in the map only, but the MotorController requires it to
     * exist.
     */
    @Override
    public void setInverted(final boolean isInverted) {
        this.wrapped.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return this.wrapped.getInverted();
    }

    @Override
    public void disable() {
        this.wrapped.disable();
    }

    @Override
    public void stopMotor() {
        this.wrapped.stopMotor();
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        this.sendable.initSendable(builder);
    }
}
