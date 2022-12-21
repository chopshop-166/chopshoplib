package com.chopshop166.chopshoplib.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

/**
 * Creator for commands.
 */
public class SubsystemCommandBuilder extends CommandBuilder {

    /** The subsystem that depends on these commands. */
    private final Subsystem subsystem;

    /**
     * Constructor.
     * 
     * @param subsystem The subsystem that the commands depend on.
     */
    public SubsystemCommandBuilder(final Subsystem subsystem) {
        super();
        this.subsystem = subsystem;
    }

    /**
     * Create a command builder with a given name.
     * 
     * @param name The command name.
     * @return A new command builder.
     */
    public BuildCommand cmd(final String name) {
        return new BuildCommand(name, subsystem);
    }

    /**
     * Create an {@link InstantCommand}.
     * 
     * @param name   The name of the command.
     * @param action The action to take.
     * @return A new command.
     */
    @Override
    public CommandBase instant(final String name, final Runnable action) {
        return new InstantCommand(action, subsystem).withName(name);
    }

    /**
     * Create a {@link RunCommand}.
     * 
     * @param name   The name of the command.
     * @param action The action to take.
     * @return A new command.
     */
    @Override
    public CommandBase running(final String name, final Runnable action) {
        return new RunCommand(action, subsystem).withName(name);
    }

    /**
     * Create a {@link StartEndCommand}.
     * 
     * @param name    The name of the command.
     * @param onStart The action to take on start.
     * @param onEnd   The action to take on end.
     * @return A new command.
     */
    @Override
    public CommandBase startEnd(final String name, final Runnable onStart, final Runnable onEnd) {
        return new StartEndCommand(onStart, onEnd, subsystem).withName(name);
    }

    /**
     * Run a {@link Runnable} and then wait until a condition is true.
     * 
     * @param name  The name of the command.
     * @param init  The action to take.
     * @param until The condition to wait until.
     * @return A new command.
     */
    @Override
    public CommandBase initAndWait(final String name, final Runnable init, final BooleanSupplier until) {
        return parallel(name, new InstantCommand(init, subsystem), new WaitUntilCommand(until));
    }

    /**
     * Create a command to run at regular intervals.
     * 
     * @param timeDelta Time in seconds to wait between calls.
     * @param periodic  The runnable to execute.
     * @return A new command.
     */
    @Override
    public CommandBase every(final double timeDelta, final Runnable periodic) {
        return new IntervalCommand(timeDelta, subsystem, periodic);
    }
}
