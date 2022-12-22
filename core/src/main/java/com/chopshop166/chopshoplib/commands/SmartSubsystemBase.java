package com.chopshop166.chopshoplib.commands;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * A {@link SmartSubsystem} that provides all the necessary convenience methods.
 */
public abstract class SmartSubsystemBase extends SubsystemBase implements SmartSubsystem {

    /** Builder for commands. */
    protected CommandBuilder make = new SubsystemCommandBuilder(this);

    /**
     * Create a command to reset the subsystem.
     * 
     * @return A reset command.
     */
    @Override
    public CommandBase resetCmd() {
        return make.instant("Reset " + SendableRegistry.getName(this), this::reset);
    }

    /**
     * Create a command to reset the subsystem.
     * 
     * @return A reset command.
     */
    @Override
    public CommandBase safeStateCmd() {
        return make.instant("Safe " + SendableRegistry.getName(this), this::safeState);
    }

    /**
     * Cancel the currently running command.
     * 
     * @return A cancel command.
     */
    @Override
    public CommandBase cancel() {
        return make.instant("Cancel " + SendableRegistry.getName(this), () -> {
        });
    }
}
