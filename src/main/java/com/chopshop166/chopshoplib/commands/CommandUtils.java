package com.chopshop166.chopshoplib.commands;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.frc2.command.Command;
import edu.wpi.first.wpilibj.frc2.command.CommandScheduler;
import edu.wpi.first.wpilibj.frc2.command.InstantCommand;
import edu.wpi.first.wpilibj.frc2.command.SendableCommandBase;
import edu.wpi.first.wpilibj.frc2.command.WaitUntilCommand;

/**
 * Utilities related to commands.
 */
final public class CommandUtils {
    private CommandUtils() {
    }

    /**
     * Repeat a {@link Command} a given number of times.
     * 
     * @param numTimesToRun The number of times to run the command.
     * @param cmd           The command to repeat.
     * @return A newly constructed command.
     */
    public static Command repeat(final int numTimesToRun, final Command cmd) {
        return new SendableCommandBase() {
            private int numTimesRun;

            @Override
            public void initialize() {
                numTimesRun++;
                cmd.schedule();
            }

            @Override
            public void execute() {
                if (!CommandScheduler.getInstance().isScheduled(cmd)) {
                    numTimesRun++;
                    cmd.schedule();
                }
            }

            @Override
            public boolean isFinished() {
                return numTimesRun >= numTimesToRun;
            }

            @Override
            public void end(final boolean interrupted) {
                numTimesRun = 0;
            }
        };
    }

    /**
     * Repeat a {@link Command} a given number of times.
     * 
     * @param numTimesToRun The number of times to run the command.
     * @param cmd           A way to create the command to repeat.
     * @return A newly constructed command group.
     */
    public static Command repeat(final int numTimesToRun, final Supplier<Command> cmd) {
        Command base = new SendableCommandBase() {
        };
        for (int i = 0; i < numTimesToRun; i++) {
            base = base.andThen(cmd.get());
        }
        return base;
    }

    /**
     * Repeatedly run a {@link Command} until a condition becomes false.
     * 
     * @param cond The condition to check against.
     * @param cmd  The command to run.
     * @return A newly created command.
     */
    public static Command repeatWhile(final BooleanSupplier cond, final Command cmd) {
        return new SendableCommandBase() {
            private boolean shouldFinish;

            @Override
            public void execute() {
                if (!CommandScheduler.getInstance().isScheduled(cmd)) {
                    if (cond.getAsBoolean()) {
                        cmd.schedule();
                    } else {
                        shouldFinish = true;
                    }
                }
            }

            @Override
            public boolean isFinished() {
                return shouldFinish;
            }
        };
    }

    /**
     * Promote a {@link Runnable} to a {@link Command}.
     * 
     * @param func The {@link Runnable} to promote.
     * @return The command.
     */
    public static Command from(final Runnable func) {
        return new InstantCommand(func);
    }

    /**
     * Fluent API to start a sequence of commands.
     * 
     * @param cmds Commands to run first.
     * @return The new command chain.
     */
    public static Command first(final Command... cmds) {
        return new SendableCommandBase() {
        }.andThen(cmds);
    }

    /**
     * Pause execution until a condition is true.
     * 
     * @param condition The case to check against.
     * @return A new command.
     */
    public static Command waitUntil(final BooleanSupplier condition) {
        return new WaitUntilCommand(condition);
    }
}