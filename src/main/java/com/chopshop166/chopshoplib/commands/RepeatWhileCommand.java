package com.chopshop166.chopshoplib.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Utilities related to commands.
 */
public class RepeatWhileCommand extends CommandBase {

    private boolean shouldFinish;
    private final BooleanSupplier cond;
    private final Command cmd;

    public RepeatWhileCommand(final BooleanSupplier cond, final Command cmd) {
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

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            cmd.end(interrupted);
        }
    }
}