package com.chopshop166.chopshoplib.commands;

import java.util.function.BooleanSupplier;

import com.chopshop166.chopshoplib.HasSafeState;
import com.chopshop166.chopshoplib.Resettable;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

/**
 * A {@link Subsystem} that provides convenience functions for common commands.
 * 
 * @see SmartSubsystemBase For class that provides wpilib-style defaults.
 */
public interface SmartSubsystem extends Subsystem, HasSafeState, Resettable, Sendable, Commandable {

    /**
     * Create a command builder with a given name.
     * 
     * @param name The command name.
     * @return A new command builder.
     */
    default BuildCommand cmd(final String name) {
        return new BuildCommand(name, this);
    }

    /**
     * Create an {@link InstantCommand}.
     * 
     * @param name   The name of the command.
     * @param action The action to take.
     * @return A new command.
     */
    @Override
    default CommandBase instant(final String name, final Runnable action) {
        return new InstantCommand(action, this).withName(name);
    }

    /**
     * Create a {@link RunCommand}.
     * 
     * @param name   The name of the command.
     * @param action The action to take.
     * @return A new command.
     */
    @Override
    default CommandBase running(final String name, final Runnable action) {
        return new RunCommand(action, this).withName(name);
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
    default CommandBase startEnd(final String name, final Runnable onStart, final Runnable onEnd) {
        return new StartEndCommand(onStart, onEnd, this).withName(name);
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
    default CommandBase initAndWait(final String name, final Runnable init, final BooleanSupplier until) {
        return parallel(name, new InstantCommand(init, this), new WaitUntilCommand(until));
    }

    /**
     * Create a command to run at regular intervals.
     * 
     * @param timeDelta Time in seconds to wait between calls.
     * @param periodic  The runnable to execute.
     * @return A new command.
     */
    @Override
    default CommandBase every(final double timeDelta, final Runnable periodic) {
        return new IntervalCommand(timeDelta, this, periodic);
    }

    /**
     * Create a command to reset the subsystem.
     * 
     * @return A reset command.
     */
    default CommandBase resetCmd() {
        return instant("Reset " + SendableRegistry.getName(this), this::reset);
    }

    /**
     * Create a command to reset the subsystem.
     * 
     * @return A reset command.
     */
    default CommandBase safeStateCmd() {
        return instant("Safe " + SendableRegistry.getName(this), this::safeState);
    }

    /**
     * Cancel the currently running command.
     * 
     * @return A cancel command.
     */
    default CommandBase cancel() {
        return instant("Cancel " + SendableRegistry.getName(this), () -> {
        });
    }
}
