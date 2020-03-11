package com.chopshop166.chopshoplib.commands;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import com.chopshop166.chopshoplib.Resettable;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

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
    public StartEndCommand startEnd(final String name, final Runnable onStart, final Runnable onEnd) {
        final StartEndCommand cmd = new StartEndCommand(onStart, onEnd, this);
        cmd.setName(name);
        return cmd;
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
        final CommandBase cmd = CommandGroupBase.parallel(new InstantCommand(init, this), new WaitUntilCommand(until));
        cmd.setName(name);
        return cmd;
    }

    /**
     * Create a command to call a consumer function.
     * 
     * @param <T>   The type to wrap.
     * @param name  The name of the command.
     * @param value The value to call the function with.
     * @param func  The function to call.
     * @return A new command.
     */
    public <T> CommandBase setter(final String name, final T value, final Consumer<T> func) {
        final InstantCommand cmd = new InstantCommand(() -> {
            func.accept(value);
        }, this);
        cmd.setName(name);
        return cmd;
    }

    /**
     * Create a command to reset the subsystem.
     * 
     * @return A reset command.
     */
    public CommandBase resetCmd() {
        return instant("Reset " + getName(), this::reset);
    }

    /**
     * Cancel the currently running command.
     * 
     * @return A cancel command.
     */
    public CommandBase cancel() {
        return instant("Cancel " + getName(), () -> {
        });
    }
}
