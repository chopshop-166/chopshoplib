package frc.team166.chopshoplib.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A declarative command class. Usable with a Runnable to create commands inside
 * the subsystems
 */
public class ActionCommand extends InstantCommand {

    private final Runnable action;

    /**
     * Create a command that calls the given action when run
     * 
     * @param action
     *            The action to take when the command is run
     */
    public ActionCommand(final Runnable action) {
        super();
        this.action = action;
    }

    /**
     * Create a command that depends on the given subsystem and calls the provided
     * action when run
     * 
     * @param subsystem
     *            The subsystem that the command depends on
     * @param action
     *            The action to take when the command is run
     */
    public ActionCommand(final Subsystem subsystem, final Runnable action) {
        super();
        useSubsystem(subsystem);
        this.action = action;
    }

    /**
     * Create a named command that calls the given action when run
     * 
     * @param name
     *            The name of the command
     * @param action
     *            The action to take when the command is run
     */
    public ActionCommand(final String name, final Runnable action) {
        super(name);
        this.action = action;
    }

    /**
     * Create a named command that depends on the given subsystem and calls the
     * provided action when run
     * 
     * @param name
     *            The name of the command
     * @param subsystem
     *            The subsystem that the command depends on
     * @param action
     *            The action to take when the command is run
     */
    public ActionCommand(final String name, final Subsystem subsystem, final Runnable action) {
        super(name);
        useSubsystem(subsystem);
        this.action = action;
    }

    /**
     * Trigger the stored action. Called just before this Command runs the first
     * time.
     */
    @Override
    protected void initialize() {
        if (action != null) {
            action.run();
        }
    }

    /**
     * Use the given subsystem's name and mark it required
     * 
     * @param subsystem
     *            The subsystem to depend on
     */
    private void useSubsystem(final Subsystem subsystem) {
        setSubsystem(subsystem.getName());
        requires(subsystem);
    }
}
