package com.chopshop166.chopshoplib.commands;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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

    /**
     * Create a command that calls the given action when run
     * 
     * @param value    The value to call the function with
     * @param consumer The function to call with the given value
     */
    public SetCommand(final T value, final Consumer<T> consumer) {
        super(() -> {
            if (consumer != null) {
                consumer.accept(value);
            }
        });
    }

    /**
     * Create a command that calls the given action when run
     * 
     * @param name     The name of the command.
     * @param value    The value to call the function with
     * @param consumer The function to call with the given value
     */
    public SetCommand(final String name, final T value, final Consumer<T> consumer) {
        this(value, consumer);
        setName(name);
    }

    /**
     * Create a command that depends on the given subsystem and calls the provided
     * action when run
     * 
     * @param subsystem The subsystem that the command depends on
     * @param value     The value to call the function with
     * @param consumer  The function to call with the given value
     */
    public SetCommand(final SubsystemBase subsystem, final T value, final Consumer<T> consumer) {
        super(() -> {
            if (consumer != null) {
                consumer.accept(value);
            }
        }, subsystem);
        setSubsystem(subsystem.getName());
    }

    /**
     * Create a command that depends on the given subsystem and calls the provided
     * action when run
     * 
     * @param name      The name of the command.
     * @param subsystem The subsystem that the command depends on
     * @param value     The value to call the function with
     * @param consumer  The function to call with the given value
     */
    public SetCommand(final String name, final SubsystemBase subsystem, final T value, final Consumer<T> consumer) {
        this(subsystem, value, consumer);
        setName(name);
    }
}
