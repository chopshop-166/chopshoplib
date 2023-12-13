package com.chopshop166.chopshoplib;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Denote a class - usually a {@link Subsystem} - that has data that can be reset.
 * <p>
 * This is primarily for things like encoders and gyros.
 */
public interface Resettable {

    /**
     * Reset this object.
     */
    default void reset() {
        // Default to not resetting state
    }

}
