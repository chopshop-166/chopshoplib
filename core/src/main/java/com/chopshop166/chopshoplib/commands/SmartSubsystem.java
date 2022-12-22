package com.chopshop166.chopshoplib.commands;

import com.chopshop166.chopshoplib.HasSafeState;
import com.chopshop166.chopshoplib.Resettable;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * A {@link Subsystem} that provides convenience functions for common commands.
 * 
 * @see SmartSubsystemBase For class that provides wpilib-style defaults.
 */
public interface SmartSubsystem extends Subsystem, HasSafeState, Resettable, Sendable {

    /**
     * Create a command to reset the subsystem.
     * 
     * @return A reset command.
     */
    default CommandBase resetCmd() {
        return new InstantCommand(this::reset, this).withName("Reset " + SendableRegistry.getName(this));
    }

    /**
     * Create a command to reset the subsystem.
     * 
     * @return A reset command.
     */
    default CommandBase safeStateCmd() {
        return new InstantCommand(this::safeState, this).withName("Safe " + SendableRegistry.getName(this));
    }

    /**
     * Cancel the currently running command.
     * 
     * @return A cancel command.
     */
    default CommandBase cancel() {
        return new InstantCommand(() -> {
        }, this).withName("Cancel " + SendableRegistry.getName(this));
    }
}
