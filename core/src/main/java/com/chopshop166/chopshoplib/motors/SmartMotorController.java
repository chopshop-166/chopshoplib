package com.chopshop166.chopshoplib.motors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import com.chopshop166.chopshoplib.sensors.IEncoder;
import com.chopshop166.chopshoplib.sensors.MockEncoder;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

/**
 * A MotorController with features attached.
 *
 * This is used in a few select scenarios, when we want to keep the exact type
 * of an object general, but at the same time want to access it as one of two
 * disconnected interfaces.
 */
public class SmartMotorController implements Sendable, MotorController {

    /** The wrapped motor controller, as a sendable object. */
    private final Sendable sendable;
    /** The wrapped motor controller. */
    private final MotorController wrapped;
    /** An encoder, if one is attached and supplied. */
    private final IEncoder encoder;
    /** Validators. */
    private final List<BooleanSupplier> validators = new ArrayList<>();

    /** Construct with mocks for everything */
    public SmartMotorController() {
        this(new MockMotorController(), new MockEncoder());
    }

    /**
     * Wrap a motor controller.
     *
     * @param <T>     The base type to wrap
     * @param wrapped The wrapped motor controller.
     */
    public <T extends Sendable & MotorController> SmartMotorController(final T wrapped) {
        this(wrapped, new MockEncoder());
    }

    /**
     * Wrap a motor controller with an encoder.
     *
     * @param <T>     The base type to wrap
     * @param wrapped The wrapped motor controller.
     * @param encoder The encoder attached to the motor controller.
     */
    public <T extends Sendable & MotorController> SmartMotorController(final T wrapped, final IEncoder encoder) {
        sendable = wrapped;
        this.wrapped = wrapped;
        this.encoder = encoder;
    }

    /**
     * Construct for a group.
     * 
     * @param controller1 The first controller.
     * @param controller2 The second controller.
     * @param controllers Subsequent controllers.
     */
    public SmartMotorController(final MotorController controller1, final MotorController controller2,
            final MotorController... controllers) {
        this(new MockEncoder(), controller1, controller2, controllers);
    }

    /**
     * Construct for a group with an encoder.
     * 
     * @param encoder     The encoder to measure with.
     * @param controller1 The first controller.
     * @param controller2 The second controller.
     * @param controllers Subsequent controllers.
     */
    public SmartMotorController(final IEncoder encoder, final MotorController controller1,
            final MotorController controller2,
            final MotorController... controllers) {
        this(grouped(controller1, controller2, controllers), encoder);
    }

    /**
     * Get the encoder attached to the robot.
     *
     * @return An encoder, or a mock if none is attached.
     */
    public IEncoder getEncoder() {
        return encoder;
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
        return validators.stream().allMatch(BooleanSupplier::getAsBoolean);
    }

    /**
     * Test if any validators failed.
     * 
     * @return Whether any validators failed.
     */
    public boolean errored() {
        return !validate();
    }

    /**
     * Add a validator.
     * 
     * @param validator The validator to test for.
     */
    public void addValidator(final BooleanSupplier validator) {
        validators.add(validator);
    }

    @Override
    public void set(final double speed) {
        wrapped.set(speed);
    }

    @Override
    public double get() {
        return wrapped.get();
    }

    /**
     * Warning: Do not use in a subsystem.
     *
     * This is intended for configuration in the map only, but the MotorController
     * requires it to exist.
     */
    @Override
    public void setInverted(final boolean isInverted) {
        wrapped.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return wrapped.getInverted();
    }

    @Override
    public void disable() {
        wrapped.disable();
    }

    @Override
    public void stopMotor() {
        wrapped.stopMotor();
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        sendable.initSendable(builder);
    }

    private static MotorControllerGroup grouped(final MotorController mc1, final MotorController mc2,
            final MotorController... mcs) {
        final MotorController[] result = new MotorController[mcs.length + 2];
        result[0] = mc1;
        result[1] = mc2;
        System.arraycopy(mcs, 0, result, 2, mcs.length);
        return new MotorControllerGroup(result);
    }
}
