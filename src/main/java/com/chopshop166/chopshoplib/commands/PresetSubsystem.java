package com.chopshop166.chopshoplib.commands;

import java.util.function.DoubleSupplier;

import com.chopshop166.chopshoplib.Resettable;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * A {@link Subsystem} that is {@link Resettable} and that provides convenience
 * functions for common commands.
 */
public abstract class PresetSubsystem<T extends Enum<?> & DoubleSupplier> extends PIDSubsystem
        implements SmartSubsystem {

    /**
     * Construct the subsystem.
     * 
     * @param controller The PID controller to use.
     */
    public PresetSubsystem(final PIDController controller) {
        super(controller);
    }

    /**
     * Construct the subsystem.
     * 
     * @param controller The PID controller to use.
     * @param initial    The initial value.
     */
    public PresetSubsystem(final PIDController controller, final double initial) {
        super(controller, initial);
    }

    /**
     * Set the preset for the subsystem.
     * 
     * @param value The preset to use.
     * @return The instant command.
     */
    public CommandBase presetCmd(final T value) {
        return new InstantCommand(() -> {
            setSetpoint(value.getAsDouble());
        }, this).withName("Set to " + value.name());
    }

    @Override
    public void reset() {
        setSetpoint(getMeasurement());
    }

}
