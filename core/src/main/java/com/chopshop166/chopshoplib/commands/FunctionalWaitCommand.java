package com.chopshop166.chopshoplib.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command that does nothing but takes a specified amount of time to finish.
 * Useful for CommandGroups. Can also be subclassed to make a command with an
 * internal timer.
 *
 */
public class FunctionalWaitCommand extends CommandBase {

    /** Used to see how much time is elapsed. */
    protected Timer timer = new Timer();

    /** Returns the duration to wait. */
    private final DoubleSupplier durationSupplier;

    /** The duration to wait. gets set when the command is initialized. */
    private double duration;

    /**
     * Creates a new FunctionalWaitCommand. This command will do nothing, and end
     * after the duration that is supplied.
     *
     * @param durationSupplier a DoubleSupplier returning the time to wait, in
     *                         seconds.
     */
    public FunctionalWaitCommand(final DoubleSupplier durationSupplier) {
        super();
        this.durationSupplier = durationSupplier;
    }

    @Override
    public void initialize() {
        this.timer.reset();
        this.timer.start();
        this.duration = this.durationSupplier.getAsDouble();
    }

    @Override
    public void end(final boolean interrupted) {
        this.timer.stop();
    }

    @Override
    public boolean isFinished() {
        return this.timer.hasElapsed(this.duration);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
