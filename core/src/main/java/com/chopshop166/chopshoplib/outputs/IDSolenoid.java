package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * An interface representing a DoubleSolenoid.
 * 
 * Unlike the WPIlib object, this interface can be extended for test values.
 */
public interface IDSolenoid extends Sendable, AutoCloseable {

    /**
     * Set the value of a solenoid.
     * 
     * @param value The value to set.
     */
    void set(Value value);

    /**
     * Read the current value of the solenoid.
     * 
     * @return The current value.
     */
    Value get();

    /**
     * Check if forward solenoid is blacklisted.
     * 
     * @return {@code true} if the solenoid is blacklisted.
     */
    boolean isFwdSolenoidDisabled();

    /**
     * Check if reverse solenoid is blacklisted.
     * 
     * @return {@code true} if the solenoid is blacklisted.
     */
    boolean isRevSolenoidDisabled();
}
