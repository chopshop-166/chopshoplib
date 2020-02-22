package com.chopshop166.chopshoplib.commands;

import java.util.function.Supplier;
import java.util.stream.Stream;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;

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
    public static CommandBase repeat(final int numTimesToRun, final Command cmd) {
        return new CommandBase() {

            // Defaults to 0
            private int numTimesRun;

            @Override
            public void initialize() {
                numTimesRun++;
                cmd.schedule();
            }

            @Override
            public void execute() {
                if (!cmd.isScheduled()) {
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
    public static CommandBase repeat(final int numTimesToRun, final Supplier<Command> cmd) {
        return CommandGroupBase.sequence(Stream.generate(cmd).limit(numTimesToRun).toArray(Command[]::new));
    }
}