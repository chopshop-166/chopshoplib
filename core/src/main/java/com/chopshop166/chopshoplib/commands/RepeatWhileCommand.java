package com.chopshop166.chopshoplib.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Utilities related to commands.
 */
public class RepeatWhileCommand extends CommandBase {

    /** True if the command should finish. */
    private boolean shouldFinish;
    /** The condition to test. */
    private final BooleanSupplier cond;
    /** The command to repeat. */
    private final Command cmd;

    /**
     * Create a command that repeats a command while a condition is true.
     * 
     * @param name The name of the command.
     * @param cmd  The command to repeat.
     * @param cond The condition to test.
     */
    public RepeatWhileCommand(final String name, final Command cmd, final BooleanSupplier cond) {
        this(cmd, cond);
        setName(name);
    }

    /**
     * Create a command that repeats a command while a condition is true.
     * 
     * @param cmd  The command to repeat.
     * @param cond The condition to test.
     */
    public RepeatWhileCommand(final Command cmd, final BooleanSupplier cond) {
        super();
        addRequirements(cmd.getRequirements().toArray(Subsystem[]::new));
        this.cond = cond;
        this.cmd = cmd;
    }

    @Override
    public void initialize() {
        shouldFinish = false;
        cmd.initialize();
    }

    @Override
    public void execute() {
        cmd.execute();
        if (cmd.isFinished()) {
            cmd.end(false);
            if (cond.getAsBoolean()) {
                cmd.initialize();
            } else {
                shouldFinish = true;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return shouldFinish;
    }

    @Override
    public void end(final boolean interrupted) {
        if (interrupted) {
            cmd.end(interrupted);
        }
    }
}