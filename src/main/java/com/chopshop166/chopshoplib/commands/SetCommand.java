package com.chopshop166.chopshoplib.commands;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.experimental.command.InstantCommand;
import edu.wpi.first.wpilibj.experimental.command.SendableSubsystemBase;

/**
 * A declarative command class.
 * <p>
 * Usable with a Consumer to create commands inside the subsystems.
 * <p>
 * Primary use is for assigning to "setter" functions.
 * 
 * @param T The type of the value to set.
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
    public SetCommand(final SendableSubsystemBase subsystem, final T value, final Consumer<T> consumer) {
        this(value, consumer);
        setSubsystem(subsystem.getName());
        addRequirements(subsystem);
    }

    /**
     * Trigger the stored action. Called just before this Command runs the first
     * time.
     */
    @Override
    public void initialize() {
        if (consumer != null) {
            consumer.accept(value);
        }
    }
}
