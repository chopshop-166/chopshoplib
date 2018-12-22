package com.chopshop166.chopshoplib;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Denote a class - usually a {@link Subsystem} - that can be reset to a default
 * safe state.
 * <p>
 * This is useful for resetting all subsystems to a "not moving" state when
 * entering a disabled mode.
 */
public interface Resettable {

    /**
     * The function to reset this object.
     */
    void reset();

}
