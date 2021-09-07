package com.chopshop166.chopshoplib.commands;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Create a command from only the functions it needs
 */
public class BuildCommand extends CommandBase {

    /** The initalize handler. */
    private Runnable onInitializeHandler = () -> {
    };
    /** The execute handler. */
    private Runnable onExecuteHandler = () -> {
    };
    /** The end handler. */
    private Consumer<Boolean> onEndHandler = interrupted -> {
    };
    /** The finished check. */
    private BooleanSupplier isFinishedHandler = () -> true;

    /**
     * Create the command builder.
     * 
     * @param name       The name of the resulting command.
     * @param subsystems All subsystems this command depends on.
     */
    public BuildCommand(final String name, final Subsystem... subsystems) {
        super();
        setName(name);
        addRequirements(subsystems);
    }

    /**
     * Set the Initialize handler.
     * 
     * @param handler The handler for initializing.
     * @return this for chaining.
     */
    public BuildCommand onInitialize(final Runnable handler) {
        this.onInitializeHandler = handler;
        return this;
    }

    /**
     * Set the Execute handler.
     * 
     * @param handler The handler for execution.
     * @return this for chaining.
     */
    public BuildCommand onExecute(final Runnable handler) {
        this.onExecuteHandler = handler;
        return this;
    }

    /**
     * Set the End handler.
     * 
     * @param handler The handler for finishing
     * @return this for chaining.
     */
    public BuildCommand onEnd(final Consumer<Boolean> handler) {
        this.onEndHandler = handler;
        return this;
    }

    /**
     * Set the finished check.
     * 
     * @param check The test for if the command is finished.
     * @return this for chaining.
     */
    public BuildCommand finishedWhen(final BooleanSupplier check) {
        this.isFinishedHandler = check;
        return this;
    }

    @Override
    final public void initialize() {
        this.onInitializeHandler.run();
    }

    @Override
    final public void execute() {
        this.onExecuteHandler.run();
    }

    @Override
    final public boolean isFinished() {
        return this.isFinishedHandler.getAsBoolean();
    }

    @Override
    final public void end(boolean interrupted) {
        this.onEndHandler.accept(interrupted);
    }
}
