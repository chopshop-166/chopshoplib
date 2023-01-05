package com.chopshop166.chopshoplib.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

/**
 * A {@link SmartSubsystem} that provides all the necessary convenience methods.
 */
public abstract class SmartSubsystemBase extends SubsystemBase implements SmartSubsystem {

    /**
     * Create a command builder with a given name.
     * 
     * @param name The command name.
     * @return A new command builder.
     */
    public BuildCommand cmd(final String name) {
        return new BuildCommand(name, this);
    }

    /**
     * Run a {@link Runnable} and then wait until a condition is true.
     * 
     * @param name  The name of the command.
     * @param init  The action to take.
     * @param until The condition to wait until.
     * @return A new command.
     */
    public CommandBase initAndWait(final String name, final Runnable init, final BooleanSupplier until) {
        return Commands.parallel(this.runOnce(init), new WaitUntilCommand(until)).withName(name);
    }

    /**
     * Create a command to run at regular intervals.
     * 
     * @param timeDelta Time in seconds to wait between calls.
     * @param periodic  The runnable to execute.
     * @return A new command.
     */
    public CommandBase every(final double timeDelta, final Runnable periodic) {
        return new IntervalCommand(timeDelta, this, periodic);
    }

    /**
     * Create a command to reset the subsystem.
     * 
     * @return A reset command.
     */
    @Override
    public CommandBase resetCmd() {
        return this.runOnce(this::reset).withName("Reset " + SendableRegistry.getName(this));
    }

    /**
     * Create a command to reset the subsystem.
     * 
     * @return A reset command.
     */
    @Override
    public CommandBase safeStateCmd() {
        return this.runOnce(this::safeState).withName("Safe " + SendableRegistry.getName(this));
    }

    /**
     * Cancel the currently running command.
     * 
     * @return A cancel command.
     */
    @Override
    public CommandBase cancel() {
        return this.runOnce(() -> {
        }).withName("Cancel " + SendableRegistry.getName(this));
    }
}
