package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.Sendable;

/**
 * An interface representing a Solenoid.
 * 
 * Unlike the WPIlib object, this interface can be extended for test values.
 */
public interface SolenoidInterface extends Sendable, AutoCloseable {

    /**
     * Set the value of a solenoid.
     * 
     * @param value The value to set.
     */
    void set(boolean value);

    /**
     * Read the current value of the solenoid.
     * 
     * @return The current value.
     */
    boolean get();

    /**
     * Check if solenoid is blacklisted.
     * 
     * @return {@code true} if the solenoid is blacklisted.
     */
    boolean isBlackListed();

    /**
     * Set the pulse duration in the PCM.
     * 
     * @param durationSeconds The duration of the pulse, from 0.01 to 2.55 seconds.
     */
    void setPulseDuration(double durationSeconds);

    /**
     * Trigger the PCM to generate a pulse of the duration set in
     * {@link #setPulseDuration}.
     */
    void startPulse();
}
