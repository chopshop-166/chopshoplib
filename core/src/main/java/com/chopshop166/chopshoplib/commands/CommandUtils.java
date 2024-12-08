package com.chopshop166.chopshoplib.commands;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.chopshop166.chopshoplib.boxes.BooleanBox;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

/**
 * Utility class for storing command helpers.
 */
final public class CommandUtils {

    private CommandUtils() {}

    /**
     * Repeat a {@link Command} a given number of times.
     *
     * @param numTimesToRun The number of times to run the command.
     * @param cmd The command to repeat.
     * @return A newly constructed command.
     */
    public static Command repeat(final int numTimesToRun, final Command cmd) {
        return repeat(numTimesToRun, cmd::asProxy);
    }

    /**
     * Repeat a {@link Command} a given number of times.
     *
     * @param numTimesToRun The number of times to run the command.
     * @param cmd A way to create the command to repeat.
     * @return A newly constructed command group.
     */
    public static Command repeat(final int numTimesToRun, final Supplier<Command> cmd) {
        return Commands.sequence(Stream.generate(cmd).limit(numTimesToRun).toArray(Command[]::new));
    }

    /**
     * Run a {@link Runnable} and then wait until a condition is true.
     *
     * @param init The action to take.
     * @param until The condition to wait until.
     * @return A new command.
     */
    public static Command initAndWait(final Runnable init, final BooleanSupplier until) {
        return Commands.runOnce(init).until(until);
    }

    /**
     * Run a {@link Runnable} repeatedly and then wait until a condition is true.
     *
     * @param func The action to take.
     * @param until The condition to wait until.
     * @return A new command.
     */
    public static Command runAndWait(final Runnable func, final BooleanSupplier until) {
        return Commands.run(func).until(until);
    }

    /**
     * Create a command to call a consumer function.
     *
     * @param <T> The type to wrap.
     * @param value The value to call the function with.
     * @param func The function to call.
     * @return A new command.
     */
    public static <T> Command setter(final T value, final Consumer<T> func) {
        return Commands.runOnce(() -> {
            func.accept(value);
        });
    }

    /**
     * Create a command to call a consumer function and wait.
     *
     * @param <T> The type to wrap.
     * @param value The value to call the function with.
     * @param func The function to call.
     * @param until The condition to wait until.
     * @return A new command.
     */
    public static <T> Command callAndWait(final T value, final Consumer<T> func,
            final BooleanSupplier until) {
        return initAndWait(() -> {
            func.accept(value);
        }, until);
    }

    /**
     * Create a command that runs only if a condition is true.
     *
     * @param condition The condition to test beforehand.
     * @param cmd The command to run.
     * @return The conditional command.
     */
    public static Command runIf(final BooleanSupplier condition, final Command cmd) {
        return Commands.either(cmd, Commands.none(), condition);
    }

    /**
     * Create a command to run at regular intervals.
     *
     * @param timeDelta Time in seconds to wait between calls.
     * @param periodic The runnable to execute.
     * @return A new command.
     */
    public static Command every(final double timeDelta, final Runnable periodic) {
        return new IntervalCommand(timeDelta, periodic);
    }

    /**
     * Create a command that waits for the duration provided by a DoubleSupplier.
     *
     * @param durationSupplier Function that returns the number of seconds to wait.
     * @return The wait command.
     */
    public static Command waitFor(final DoubleSupplier durationSupplier) {
        return new FunctionalWaitCommand(durationSupplier);
    }

    /**
     * Run a command, running one command if the first is interrupted, or a second otherwise.
     *
     * @param original The command to test.
     * @param ifInterrupted The command to run if original was interrupted.
     * @param ifFinished The command to run if original was not interrupted.
     * @return A command object.
     */
    public static Command doIfInterrupted(final Command original, final Command ifInterrupted,
            final Command ifFinished) {
        final BooleanBox wasInterrupted = new BooleanBox();
        return Commands.runOnce(wasInterrupted::reset).andThen(
                original.finallyDo(wasInterrupted::accept),
                Commands.either(ifInterrupted, ifFinished, wasInterrupted));
    }

    /**
     * Run a command, running one command if the first times out, or a second otherwise.
     *
     * @param original The command to test.
     * @param timeout The timeout time.
     * @param ifInterrupted The command to run if original was interrupted.
     * @param ifFinished The command to run if original was not interrupted.
     * @return A command object.
     */
    public static Command doIfInterrupted(final Command original, final double timeout,
            final Command ifInterrupted, final Command ifFinished) {
        return doIfInterrupted(original.withTimeout(timeout), ifInterrupted, ifFinished);
    }

}
