package com.chopshop166.chopshoplib.commands;

import static com.chopshop166.chopshoplib.RobotUtils.getValueOrDefault;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import com.chopshop166.chopshoplib.Resettable;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

/**
 * A {@link Subsystem} that is {@link Resettable} and that provides convenience
 * functions for common commands.
 * 
 * @see SmartSubsystemBase For class that provides wpilib-style defaults.
 */
public interface SmartSubsystem extends Subsystem, Resettable, Sendable {

    /**
     * Create a command builder with a given name.
     * 
     * @param name The command name.
     * @return A new command builder.
     */
    default CommandBuilder cmd(final String name) {
        return new CommandBuilder(name, this);
    }

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
    default CommandBase functional(final String name, final Runnable onInit, final Runnable onExecute,
            final Consumer<Boolean> onEnd, final BooleanSupplier isFinished) {
        final Runnable realOnInit = getValueOrDefault(onInit, () -> {
        });
        final Runnable realOnExec = getValueOrDefault(onExecute, () -> {
        });
        final Consumer<Boolean> realOnEnd = getValueOrDefault(onEnd, interrupted -> {
        });
        final BooleanSupplier realIsFinished = getValueOrDefault(isFinished, () -> true);
        return new FunctionalCommand(realOnInit, realOnExec, realOnEnd, realIsFinished, this).withName(name);
    }

    /**
     * Create an {@link InstantCommand}.
     * 
     * @param name   The name of the command.
     * @param action The action to take.
     * @return A new command.
     */
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
    default CommandBase initAndWait(final String name, final Runnable init, final BooleanSupplier until) {
        return CommandRobot.parallel(name, new InstantCommand(init, this), new WaitUntilCommand(until));
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
    default <T> CommandBase setter(final String name, final T value, final Consumer<T> func) {
        return new InstantCommand(() -> {
            func.accept(value);
        }, this).withName(name);
    }

    /**
     * Create a command to call a consumer function and wait.
     * 
     * @param <T>   The type to wrap.
     * @param name  The name of the command.
     * @param value The value to call the function with.
     * @param func  The function to call.
     * @param until The condition to wait until.
     * @return A new command.
     */
    default <T> CommandBase callAndWait(final String name, final T value, final Consumer<T> func,
            final BooleanSupplier until) {
        return initAndWait(name, () -> {
            func.accept(value);
        }, until);
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
     * Cancel the currently running command.
     * 
     * @return A cancel command.
     */
    default CommandBase cancel() {
        return instant("Cancel " + SendableRegistry.getName(this), () -> {
        });
    }
}
