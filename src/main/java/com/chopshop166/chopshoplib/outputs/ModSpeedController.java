package com.chopshop166.chopshoplib.outputs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.chopshop166.chopshoplib.sensors.IEncoder;
import com.chopshop166.chopshoplib.sensors.MockEncoder;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * {@link SpeedController} that has modifiers applied to it.
 *
 * This is a generic system that can support many changes to speed.
 */
public class ModSpeedController implements SmartSpeedController {

    final private SmartSpeedController wrapped;
    final private List<Modifier> modifiers;

    private IEncoder encoder = new MockEncoder();

    /**
     * Wrap a speed controller in limits.
     * 
     * @param wrapped   The speed controller to limit.
     * @param modifiers Modifiers to use by default.
     */
    public ModSpeedController(final SmartSpeedController wrapped, final Modifier... modifiers) {
        super();
        this.wrapped = wrapped;
        this.modifiers = new ArrayList<>(Arrays.asList(modifiers));
    }

    /**
     * Wrap a speed controller in limits.
     * 
     * @param <T>       A speed controller type.
     * @param wrapped   The speed controller to limit.
     * @param modifiers Modifiers to use by default.
     */
    public <T extends Sendable & SpeedController> ModSpeedController(final T wrapped, final Modifier... modifiers) {
        this(SmartSpeedController.wrap(wrapped), modifiers);
    }

    /**
     * Wrap a speed controller in limits.
     * 
     * @param wrapped   The speed controller to limit.
     * @param encoder   Encoder object attached to the speed controller.
     * @param modifiers Modifiers to use by default.
     */
    public ModSpeedController(final SmartSpeedController wrapped, final IEncoder encoder, final Modifier... modifiers) {
        this(wrapped, modifiers);
        this.encoder = encoder;
    }

    /**
     * Wrap a speed controller in limits.
     * 
     * @param <T>       A speed controller type.
     * @param wrapped   The speed controller to limit.
     * @param encoder   Encoder object attached to the speed controller.
     * @param modifiers Modifiers to use by default.
     */
    public <T extends Sendable & SpeedController> ModSpeedController(final T wrapped, final IEncoder encoder,
            final Modifier... modifiers) {
        this(SmartSpeedController.wrap(wrapped), encoder, modifiers);
    }

    /**
     * Get the original speed controller.
     * 
     * @return The wrapped object.
     */
    public SmartSpeedController getWrapped() {
        return wrapped;
    }

    /**
     * Add modifiers to the speed controller.
     * 
     * @param m  First modifier.
     * @param ms Any extra modifiers (optional).
     */
    public void add(final Modifier m, final Modifier... ms) {
        modifiers.add(m);
        modifiers.addAll(Arrays.asList(ms));
    }

    /**
     * Add all modifiers from a collection.
     * 
     * @param ms Collection of modifiers.
     */
    public void addAll(final Collection<? extends Modifier> ms) {
        modifiers.addAll(ms);
    }

    /**
     * Set the encoder that this speed controller uses.
     * 
     * @param enc The encoder.
     */
    public void setEncoder(final IEncoder enc) {
        encoder = enc;
    }

    @Override
    public void set(final double speed) {
        wrapped.set(calculate(speed));
    }

    @Override
    public double get() {
        return wrapped.get();
    }

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
    public void pidWrite(final double output) {
        wrapped.set(output);
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        wrapped.initSendable(builder);
    }

    @Override
    public IEncoder getEncoder() {
        return encoder;
    }

    /**
     * Run all modifiers.
     * 
     * As modifiers could have side effects, this is private.
     * 
     * @param rawSpeed The base speed to run
     * @return The new speed
     */
    private double calculate(final double rawSpeed) {
        double speed = rawSpeed;
        for (final Modifier m : modifiers) {
            speed = m.applyAsDouble(speed);
        }
        return speed;
    }
}
