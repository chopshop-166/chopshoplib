package frc.team166.chopshoplib.commands;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A declarative command class. Usable with a Consumer to create commands inside
 * the subsystems. Primary use is for assigning to "setter" functions.
 */
public class SetCommand<T> extends InstantCommand {

    private final Consumer<T> consumer;
    private final T value;

    /**
     * Create a command that calls the given action when run
     * 
     * @param value    The value to call the function with
     * @param consumer The function to call with the given value
     */
    public SetCommand(final T value, final Consumer<T> consumer) {
        super();
        this.value = value;
        this.consumer = consumer;
    }

    /**
     * Create a command that depends on the given subsystem and calls the provided
     * action when run
     * 
     * @param subsystem The subsystem that the command depends on
     * @param value     The value to call the function with
     * @param consumer  The function to call with the given value
     */
    public SetCommand(final Subsystem subsystem, final T value, final Consumer<T> consumer) {
        this(value, consumer);
        useSubsystem(subsystem);
    }

    /**
     * Create a named command that calls the given action when run
     * 
     * @param name     The name of the command
     * @param value    The value to call the function with
     * @param consumer The function to call with the given value
     */
    public SetCommand(final String name, final T value, final Consumer<T> consumer) {
        this(value, consumer);
        setName(name);
    }

    /**
     * Create a named command that depends on the given subsystem and calls the
     * provided action when run
     * 
     * @param name      The name of the command
     * @param subsystem The subsystem that the command depends on
     * @param value     The value to call the function with
     * @param consumer  The function to call with the given value
     */
    public SetCommand(final String name, final Subsystem subsystem, final T value, final Consumer<T> consumer) {
        this(value, consumer);
        setName(name);
        useSubsystem(subsystem);
    }

    /**
     * Trigger the stored action. Called just before this Command runs the first
     * time.
     */
    @Override
    protected void initialize() {
        if (consumer != null) {
            consumer.accept(value);
        }
    }

    /**
     * Use the given subsystem's name and mark it required
     * 
     * @param subsystem The subsystem to depend on
     */
    private void useSubsystem(final Subsystem subsystem) {
        setSubsystem(subsystem.getName());
        requires(subsystem);
    }
}
