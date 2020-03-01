package com.chopshop166.chopshoplib;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * A {@link Subsystem} that is {@link Resettable} and that provides convenience
 * functions for common commands.
 */
public abstract class SmartSubsystem extends SubsystemBase implements Resettable {

    /**
     * Create a {@link FunctionalCommand} with a name.
     * 
     * @param name       The command name.
     * @param onInit     The action to run on initialize. If null, then will do
     *                   nothing.
     * @param onExecute  The action to run on execute. If null, then will do
     *                   nothing.
     * @param onEnd      The action to run on end. If null, then will do nothing.
     * @param isFinished The condition to end the command.
     * @return A new command.
     */
    public FunctionalCommand functional(final String name, final Runnable onInit, final Runnable onExecute,
            final Consumer<Boolean> onEnd, final BooleanSupplier isFinished) {
        final FunctionalCommand cmd = new FunctionalCommand(onInit == null ? () -> {
        } : onInit, onExecute == null ? () -> {
        } : onExecute, onEnd == null ? interrupted -> {
        } : onEnd, isFinished, this);
        cmd.setName(name);
        return cmd;
    }

    /**
     * Create an {@link InstantCommand}.
     * 
     * @param name   The name of the command.
     * @param action The action to take.
     * @return A new command.
     */
    public InstantCommand instant(final String name, final Runnable action) {
        final InstantCommand cmd = new InstantCommand(action, this);
        cmd.setName(name);
        return cmd;
    }

    /**
     * Create a {@link RunCommand}.
     * 
     * @param name   The name of the command.
     * @param action The action to take.
     * @return A new command.
     */
    public RunCommand running(final String name, final Runnable action) {
        final RunCommand cmd = new RunCommand(action, this);
        cmd.setName(name);
        return cmd;
    }

    /**
     * Create a {@link StartEndCommand}.
     * 
     * @param name    The name of the command.
     * @param onStart The action to take on start.
     * @param onEnd   The action to take on end.
     * @return A new command.
     */
    public StartEndCommand startend(final String name, final Runnable onStart, final Runnable onEnd) {
        final StartEndCommand cmd = new StartEndCommand(onStart, onEnd, this);
        cmd.setName(name);
        return cmd;
    }

    /**
     * Create a command to reset the subsystem.
     * 
     * @return A new command.
     */
    public CommandBase resetCmd() {
        return instant("Reset " + getName(), this::reset);
    }
}
