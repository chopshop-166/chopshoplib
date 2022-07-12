package com.chopshop166.chopshoplib.commands;

import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ProxyScheduleCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

/**
 * Utilities related to commands.
 */
public interface Commandable {

    /**
     * Repeat a {@link Command} a given number of times.
     *
     * @param name          The name of the command.
     * @param numTimesToRun The number of times to run the command.
     * @param cmd           The command to repeat.
     * @return A newly constructed command.
     */
    default CommandBase repeat(final String name, final int numTimesToRun, final Command cmd) {
        return repeat(numTimesToRun, cmd).withName(name);
    }

    /**
     * Repeat a {@link Command} a given number of times.
     *
     * @param numTimesToRun The number of times to run the command.
     * @param cmd           The command to repeat.
     * @return A newly constructed command.
     */
    default CommandBase repeat(final int numTimesToRun, final Command cmd) {
        return repeat(numTimesToRun, () -> new ProxyScheduleCommand(cmd));
    }

    /**
     * Repeat a {@link Command} a given number of times.
     *
     * @param name          The name of the command.
     * @param numTimesToRun The number of times to run the command.
     * @param cmd           A way to create the command to repeat.
     * @return A newly constructed command group.
     */
    default CommandBase repeat(final String name, final int numTimesToRun, final Supplier<Command> cmd) {
        return repeat(numTimesToRun, cmd).withName(name);
    }

    /**
     * Repeat a {@link Command} a given number of times.
     *
     * @param numTimesToRun The number of times to run the command.
     * @param cmd           A way to create the command to repeat.
     * @return A newly constructed command group.
     */
    default CommandBase repeat(final int numTimesToRun, final Supplier<Command> cmd) {
        return CommandGroupBase.sequence(Stream.generate(cmd).limit(numTimesToRun).toArray(Command[]::new));
    }

    /**
     * Create a command builder with a given name.
     *
     * @param name         The command name.
     * @param requirements The subsystems that the command needs (can be empty).
     * @return A new command builder.
     */
    default BuildCommand cmd(final String name, final Subsystem... requirements) {
        return new BuildCommand(name, requirements);
    }

    /**
     * Create an {@link InstantCommand}.
     *
     * @param name   The name of the command.
     * @param action The action to take.
     * @return A new command.
     */
    default CommandBase instant(final String name, final Runnable action) {
        return new InstantCommand(action).withName(name);
    }

    /**
     * Create a {@link RunCommand}.
     *
     * @param name   The name of the command.
     * @param action The action to take.
     * @return A new command.
     */
    default CommandBase running(final String name, final Runnable action) {
        return new RunCommand(action).withName(name);
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
        return new StartEndCommand(onStart, onEnd).withName(name);
    }

    /**
     * Wait until a condition is true.
     *
     * @param name  The name of the command.
     * @param until The condition to wait until.
     * @return A new command.
     */
    default CommandBase waitUntil(final String name, final BooleanSupplier until) {
        return new WaitUntilCommand(until).withName(name);
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
        return parallel(name, new InstantCommand(init), new WaitUntilCommand(until));
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
        return instant(name, () -> {
            func.accept(value);
        });
    }

    /**
     * Create a command that sets a motor to a speed while the command is running.
     *
     * @param name  The name of the command.
     * @param value The value to set the motor to.
     * @param motor The motor to use.
     * @return A new command.
     */
    default CommandBase runWhile(final String name, final double value, final MotorController motor) {
        return startEnd(name, () -> {
            motor.set(value);
        }, motor::stopMotor);
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
     * Create a sequential command group with a name.
     *
     * @param name The name of the command group.
     * @param cmds The commands to sequence.
     * @return A new command group.
     */
    default CommandBase sequence(final String name, final Command... cmds) {
        return CommandGroupBase.sequence(cmds).withName(name);
    }

    /**
     * Create a parallel command group with a name.
     *
     * @param name The name of the command group.
     * @param cmds The commands to run in parallel.
     * @return A new command group.
     */
    default CommandBase parallel(final String name, final Command... cmds) {
        return CommandGroupBase.parallel(cmds).withName(name);
    }

    /**
     * Create a racing command group with a name.
     *
     * @param name   The name of the command group.
     * @param racers The commands to race.
     * @return A new command group.
     */
    default CommandBase race(final String name, final Command... racers) {
        return CommandGroupBase.race(racers).withName(name);
    }

    /**
     * Create a deadline-limited command group with a name.
     *
     * @param name    The name of the command group.
     * @param limiter The deadline command.
     * @param cmds    The commands to run until the deadline ends.
     * @return A new command group.
     */
    default CommandBase deadline(final String name, final Command limiter, final Command... cmds) {
        return CommandGroupBase.deadline(limiter, cmds).withName(name);
    }

    /**
     * Create a conditional command.
     *
     * @param condition The condition to test.
     * @param onTrue    The command to run if the condition is true.
     * @param onFalse   The command to run if the condition is false.
     * @return The conditional command.
     */
    default CommandBase conditional(final BooleanSupplier condition, final Command onTrue, final Command onFalse) {
        return new ConditionalCommand(onTrue, onFalse, condition);
    }

    /**
     * Create a command that runs only if a condition is true.
     * 
     * @param condition The condition to test beforehand.
     * @param cmd       The command to run.
     * @return The conditional command.
     */
    default CommandBase runIf(final BooleanSupplier condition, final Command cmd) {
        return conditional(condition, cmd, new InstantCommand());
    }

    /**
     * Create a command that selects which command to run from a map.
     *
     * @param name     The command's name.
     * @param commands The possible commands to run.
     * @param selector The function to determine which command should be run.
     * @return The wrapper command object.
     */
    default CommandBase select(final String name, final Map<Object, Command> commands,
            final Supplier<Object> selector) {
        return new SelectCommand(commands, selector).withName(name);
    }

    /**
     * Create a command that selects which command to run from a function.
     *
     * @param name     The command's name.
     * @param selector The function to determine which command should be run.
     * @return The wrapper command object.
     */
    default CommandBase select(final String name, final Supplier<Command> selector) {
        return new SelectCommand(selector).withName(name);
    }

    /**
     * Create a command to run at regular intervals.
     * 
     * @param timeDelta Time in seconds to wait between calls.
     * @param periodic  The runnable to execute.
     * @return A new command.
     */
    default CommandBase every(final double timeDelta, final Runnable periodic) {
        return new IntervalCommand(timeDelta, periodic);
    }

    /**
     * Create a command that waits for the duration provided by a DoubleSupplier
     * 
     * @param name             The command's name
     * @param durationSupplier Function that returns the number of seconds to wait
     * @return The wait command
     */
    default CommandBase waitFor(final String name, final DoubleSupplier durationSupplier) {
        return new FunctionalWaitCommand(name, durationSupplier);
    }

    /**
     * Create a command that waits for the duration provided by a DoubleSupplier
     * 
     * @param durationSupplier Function that returns the number of seconds to wait
     * @return The wait command
     */
    default CommandBase waitFor(final DoubleSupplier durationSupplier) {
        return new FunctionalWaitCommand(durationSupplier);
    }

    /**
     * Create a WaitCommand
     * 
     * @param duration The duration in seconds
     * @return The wait command
     */
    default CommandBase waitFor(final double duration) {
        return new WaitCommand(duration);
    }

}
