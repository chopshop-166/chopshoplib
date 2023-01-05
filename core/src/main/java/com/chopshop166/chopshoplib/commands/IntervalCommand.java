package com.chopshop166.chopshoplib.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * A command that executes a runnable each time a given time span has elapsed.
 */
public class IntervalCommand extends CommandBase {

    /** Advances when time has passed, triggers the runnable. */
    private final Timer elapsedTimer = new Timer();
    /** The time delta to check for. */
    private final double timeDelta;
    /** The action to take. */
    private final Runnable periodic;

    /**
     * Create the command.
     * 
     * @param timeDelta Time in seconds to wait between calls.
     * @param periodic  The runnable to execute.
     */
    public IntervalCommand(final double timeDelta, final Runnable periodic) {
        super();
        this.timeDelta = timeDelta;
        this.periodic = periodic;
    }

    /**
     * Create the command.
     * 
     * @param timeDelta   Time in seconds to wait between calls.
     * @param requirement The subsystem that the command depends on.
     * @param periodic    The runnable to execute.
     */
    public IntervalCommand(final double timeDelta, final Subsystem requirement, final Runnable periodic) {
        this(timeDelta, periodic);
        this.addRequirements(requirement);
    }

    @Override
    public void initialize() {
        this.elapsedTimer.reset();
        this.elapsedTimer.start();
    }

    @Override
    public void execute() {
        if (this.elapsedTimer.advanceIfElapsed(this.timeDelta)) {
            this.periodic.run();
        }
    }

    @Override
    public void end(final boolean interrupted) {
        this.elapsedTimer.stop();
    }
}
