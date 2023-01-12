package com.chopshop166.chopshoplib.commands;

import static edu.wpi.first.wpilibj2.command.Commands.parallel;
import static edu.wpi.first.wpilibj2.command.Commands.waitUntil;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * A {@link SmartSubsystem} that provides all the necessary convenience methods.
 */
public abstract class SmartSubsystemBase extends SubsystemBase implements SmartSubsystem {

    /**
     * Create a command builder with a given name.
     * 
     * @return A new command builder.
     */
    public BuildCommand cmd() {
        return new BuildCommand(this);
    }

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
     * @param init The action to take.
     * @param until The condition to wait until.
     * @return A new command.
     */
    public CommandBase initAndWait(final Runnable init, final BooleanSupplier until) {
        return parallel(this.runOnce(init), waitUntil(until));
    }

    /**
     * Create a command to run at regular intervals.
     * 
     * @param timeDelta Time in seconds to wait between calls.
     * @param periodic The runnable to execute.
     * @return A new command.
     */
    public CommandBase every(final double timeDelta, final Runnable periodic) {
        return new IntervalCommand(timeDelta, this, periodic);
    }

    /**
     * Cancel the currently running command.
     * 
     * @return A cancel command.
     */
    @Override
    public CommandBase cancel() {
        return this.runOnce(() -> {
        }).withName("Cancel " + this.getName());
    }
}
