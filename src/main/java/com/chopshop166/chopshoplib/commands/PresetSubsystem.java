package com.chopshop166.chopshoplib.commands;

import java.util.function.DoubleSupplier;

import com.chopshop166.chopshoplib.PersistenceCheck;
import com.chopshop166.chopshoplib.Resettable;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

/**
 * A {@link Subsystem} that is {@link Resettable} and that provides convenience
 * functions for common commands.
 */
public abstract class PresetSubsystem<T extends Enum<?> & DoubleSupplier> extends PIDSubsystem
        implements SmartSubsystem {

    /** Check to make sure it's at the setpoint for enough time */
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
        return new InstantCommand(() -> {
            setSetpoint(value.getAsDouble());
        }, this).withName("Set to " + value.name());
    }

    /**
     * Wait until the subsystem is moved to the proper setpoint.
     * 
     * @return The wait command.
     */
    public CommandBase waitForSetpoint() {
        final CommandBase ret = new WaitUntilCommand(persistenceCheck).withName("Wait For Setpoint");
        ret.addRequirements(this);
        return ret;
    }

    /**
     * Set the preset for the subsystem, and wait until it moves to that point.
     * 
     * @param value The preset to use.
     * @return The instant command.
     */
    public CommandBase presetWait(final T value) {
        return CommandRobot.sequence("Set to " + value.name(), presetCmd(value), waitForSetpoint());
    }

    @Override
    public void reset() {
        setSetpoint(getMeasurement());
    }

}
