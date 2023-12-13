package com.chopshop166.chopshoplib;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Denote a class - usually a {@link Subsystem} - that can be reset to a default safe state.
 * <p>
 * This is useful for resetting all subsystems to a "not moving" state when entering a disabled
 * mode.
 */
@FunctionalInterface
public interface HasSafeState {

    /**
     * Go to a safe state.
     */
    void safeState();

}
