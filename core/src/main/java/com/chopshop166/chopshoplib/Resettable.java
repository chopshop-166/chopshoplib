package com.chopshop166.chopshoplib;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Denote a class - usually a {@link Subsystem} - that can be reset to a default
 * safe state.
 * <p>
 * This is useful for resetting all subsystems to a "not moving" state when
 * entering a disabled mode.
 */
public interface Resettable {

    /**
     * Reset this object.
     */
    default void reset() {
        // Default to not resetting state
    }

    /**
     * Reset this object.
     * 
     * Ignores its parameter - useful for passing as the end function of a command.
     * 
     * @param interrupted Ignored.
     */
    default void reset(final boolean interrupted) {
        this.reset();
    }

}
