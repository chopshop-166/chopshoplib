package com.chopshop166.chopshoplib.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A command that waits for a {@link BooleanSupplier} to return true.
 */
public class BoolCommand extends Command {

    private final BooleanSupplier supplier;

    /**
     * Create the command.
     * 
     * @param supplier The accessor.
     */
    public BoolCommand(final BooleanSupplier supplier) {
        super();
        this.supplier = supplier;
    }

    /**
     * Create the command and require a subsystem.
     * 
     * @param subsystem The subsystem that the command depends on.
     * @param supplier  The accessor.
     */
    public BoolCommand(final Subsystem subsystem, final BooleanSupplier supplier) {
        this(supplier);
        useSubsystem(subsystem);
    }

    /**
     * Create the command with a given name.
     * 
     * @param name     The name of the command.
     * @param supplier The accessor.
     */
    public BoolCommand(final String name, final BooleanSupplier supplier) {
        this(supplier);
        setName(name);
    }

    /**
     * Create the command with a given name that requires the provided subsystem.
     * 
     * @param name      The name of the command.
     * @param subsystem The subsystem that the command depends on.
     * @param supplier  The accessor.
     */
    public BoolCommand(final String name, final Subsystem subsystem, final BooleanSupplier supplier) {
        this(supplier);
        setName(name);
        useSubsystem(subsystem);
    }

    /**
     * Return true when the supplier returns true.
     */
    @Override
    protected boolean isFinished() {
        return supplier.getAsBoolean();
    }

    /**
     * Use the given subsystem's name and mark it required.
     * 
     * @param subsystem The subsystem to depend on.
     */
    private void useSubsystem(final Subsystem subsystem) {
        setSubsystem(subsystem.getName());
        requires(subsystem);
    }
}
