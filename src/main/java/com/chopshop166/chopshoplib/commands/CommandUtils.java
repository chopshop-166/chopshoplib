package com.chopshop166.chopshoplib.commands;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

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
        return new Command() {
            private int numTimesRun;

            @Override
            protected void initialize() {
                numTimesRun++;
                cmd.start();
            }

            @Override
            protected void execute() {
                if (cmd.isCompleted()) {
                    numTimesRun++;
                    cmd.start();
                }
            }

            @Override
            protected boolean isFinished() {
                return numTimesRun >= numTimesToRun;
            }

            @Override
            protected void end() {
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
        class RepeatedCommandGroup extends CommandGroup {
            public RepeatedCommandGroup() {
                super();
                for (int i = 0; i < numTimesToRun; i++) {
                    addSequential(cmd.get());
                }
            }
        }
        return new RepeatedCommandGroup();
    }

    /**
     * Repeat a {@link Command} a given number of times.
     * 
     * @param numTimesToRun The number of times to run the command.
     * @param cmd           A way to create the command to repeat.
     * @return A newly constructed command group.
     */
    public static Command repeat(final String name, final int numTimesToRun, final Supplier<Command> cmd) {
        class RepeatedCommandGroup extends CommandGroup {
            public RepeatedCommandGroup() {
                super(name);
                for (int i = 0; i < numTimesToRun; i++) {
                    addSequential(cmd.get());
                }
            }
        }
        return new RepeatedCommandGroup();
    }

    /**
     * Repeatedly run a {@link Command} until a condition becomes false.
     * 
     * @param cond The condition to check against.
     * @param cmd  The command to run.
     * @return A newly created command.
     */
    public static Command repeatWhile(final BooleanSupplier cond, final Command cmd) {
        return new Command() {
            private boolean shouldFinish;

            @Override
            protected void execute() {
                if (!cmd.isRunning()) {
                    if (cond.getAsBoolean()) {
                        cmd.start();
                    } else {
                        shouldFinish = true;
                    }
                }
            }

            @Override
            protected boolean isFinished() {
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
     * Fluent API to start a {@link CommandChain}.
     * 
     * @param cmds Commands to run first.
     * @return The new command chain.
     */
    public static Command first(final Command... cmds) {
        return new CommandChain(cmds);
    }

    /**
     * Pause execution until a condition is true.
     * 
     * @param condition The case to check against.
     * @return A new command.
     */
    public static Command waitUntil(final BooleanSupplier condition) {
        return new Command("Wait Until Condition") {
            @Override
            protected boolean isFinished() {
                return condition.getAsBoolean();
            }
        };
    }
}