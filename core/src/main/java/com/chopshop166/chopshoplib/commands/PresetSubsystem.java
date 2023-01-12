package com.chopshop166.chopshoplib.commands;

import java.util.function.DoubleSupplier;

import com.chopshop166.chopshoplib.PersistenceCheck;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

/**
 * A {@link PIDSubsystem} that has several presets that it can go to.
 */
public abstract class PresetSubsystem<T extends Enum<?> & DoubleSupplier> extends PIDSubsystem
        implements SmartSubsystem {

    /** Check to make sure it's at the setpoint for enough time. */
    private final PersistenceCheck persistenceCheck;

    /**
     * Construct the subsystem.
     * 
     * @param controller The PID controller to use.
     * @param numSamples Number of setpoint valid samples to check for.
     */
    public PresetSubsystem(final PIDController controller, final int numSamples) {
        super(controller);
        this.persistenceCheck = new PersistenceCheck(numSamples, controller::atSetpoint);
    }

    /**
     * Construct the subsystem.
     * 
     * @param controller The PID controller to use.
     * @param initial The initial value.
     * @param numSamples Number of setpoint valid samples to check for.
     */
    public PresetSubsystem(final PIDController controller, final int numSamples,
            final double initial) {
        super(controller, initial);
        this.persistenceCheck = new PersistenceCheck(numSamples, controller::atSetpoint);
    }

    /**
     * Set the preset for the subsystem.
     * 
     * @param value The preset to use.
     * @return The instant command.
     */
    public CommandBase presetCmd(final T value) {
        return this.runOnce(() -> {
            this.setSetpoint(value.getAsDouble());
        }).withName("Set to " + value.name());
    }

    /**
     * Wait until the subsystem is moved to the proper setpoint.
     * 
     * @return The wait command.
     */
    public CommandBase waitForSetpoint() {
        return Commands.waitUntil(this.persistenceCheck).withName("Wait For Setpoint");
    }

    /**
     * Set the preset for the subsystem, and wait until it moves to that point.
     * 
     * @param value The preset to use.
     * @return The instant command.
     */
    public CommandBase presetWait(final T value) {
        return Commands.sequence(this.presetCmd(value), this.waitForSetpoint())
                .withName("Set to " + value.name());
    }

    @Override
    public void safeState() {
        this.setSetpoint(this.getMeasurement());
    }

    @Override
    public void reset() {
        this.setSetpoint(this.getMeasurement());
    }

}
