package com.chopshop166.chopshoplib.commands;

import java.util.Arrays;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Create a command from only the functions it needs
 */
public class CommandBuilder {

    /** The name of the resulting command. */
    private final String name;
    /** The subsystems that the command depends on. */
    private final Subsystem[] dependencies;
    /** The initalize handler. */
    private Runnable onInitialize = () -> {
    };
    /** The execute handler. */
    private Runnable onExecute = () -> {
    };
    /** The end handler. */
    private Consumer<Boolean> onEndHandler = interrupted -> {
    };
    /** The finished check. */
    private BooleanSupplier checkIsFinished = () -> true;

    /**
     * Create the command builder.
     * 
     * @param name       The name of the resulting command.
     * @param subsystems All subsystems this command depends on.
     */
    public CommandBuilder(final String name, final Subsystem... subsystems) {
        this.name = name;
        dependencies = Arrays.copyOf(subsystems, subsystems.length);
    }

    /**
     * Set the Initialize handler
     * 
     * @param onInitialize The handler for initializing.
     * @return this for chaining
     */
    public CommandBuilder onInit(final Runnable onInitialize) {
        this.onInitialize = onInitialize;
        return this;
    }

    /**
     * Set the Execute handler
     * 
     * @param onExecute The handler for execution.
     * @return this for chaining
     */
    public CommandBuilder onExec(final Runnable onExecute) {
        this.onExecute = onExecute;
        return this;
    }

    /**
     * Set the End handler
     * 
     * @param onEndHandler The handler for finishing
     * @return this for chaining
     */
    public CommandBuilder onEnd(final Consumer<Boolean> onEndHandler) {
        this.onEndHandler = onEndHandler;
        return this;
    }

    /**
     * Set the finished check.
     * 
     * @param checkIsFinished The test for if the command is finished.
     * @return this for chaining
     */
    public CommandBuilder finishedWhen(final BooleanSupplier checkIsFinished) {
        this.checkIsFinished = checkIsFinished;
        return this;
    }

    /**
     * Build the final command.
     * 
     * @return The built command.
     */
    public CommandBase build() {
        return new FunctionalCommand(onInitialize, onExecute, onEndHandler, checkIsFinished, dependencies)
                .withName(name);
    }
}
