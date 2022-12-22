package com.chopshop166.chopshoplib.commands;

import java.util.function.DoubleSupplier;

import com.chopshop166.chopshoplib.PersistenceCheck;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

/**
 * A {@link PIDSubsystem} that has several presets that it can go to.
 */
public abstract class PresetSubsystem<T extends Enum<?> & DoubleSupplier> extends PIDSubsystem
        implements SmartSubsystem {

    /** Builder for commands. */
    protected CommandBuilder make = new SubsystemCommandBuilder(this);
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
        persistenceCheck = new PersistenceCheck(numSamples, controller::atSetpoint);
    }

    /**
     * Construct the subsystem.
     * 
     * @param controller The PID controller to use.
     * @param initial    The initial value.
     * @param numSamples Number of setpoint valid samples to check for.
     */
    public PresetSubsystem(final PIDController controller, final int numSamples, final double initial) {
        super(controller, initial);
        persistenceCheck = new PersistenceCheck(numSamples, controller::atSetpoint);
    }

    /**
     * Set the preset for the subsystem.
     * 
     * @param value The preset to use.
     * @return The instant command.
     */
    public CommandBase presetCmd(final T value) {
        return make.instant("Set to " + value.name(), () -> {
            setSetpoint(value.getAsDouble());
        });
    }

    /**
     * Wait until the subsystem is moved to the proper setpoint.
     * 
     * @return The wait command.
     */
    public CommandBase waitForSetpoint() {
        return make.waitUntil("Wait For Setpoint", persistenceCheck);
    }

    /**
     * Set the preset for the subsystem, and wait until it moves to that point.
     * 
     * @param value The preset to use.
     * @return The instant command.
     */
    public CommandBase presetWait(final T value) {
        return make.sequence("Set to " + value.name(), presetCmd(value), waitForSetpoint());
    }

    @Override
    public void safeState() {
        setSetpoint(getMeasurement());
    }

    @Override
    public void reset() {
        setSetpoint(getMeasurement());
    }

}
