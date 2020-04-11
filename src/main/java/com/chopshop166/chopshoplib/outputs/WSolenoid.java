package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * An instance of {@link Solenoid} that can be mocked.
 */
public final class WSolenoid extends Solenoid implements ISolenoid {

    /**
     * Construct the solenoid.
     * 
     * @param channel The channel to use.
     */
    public WSolenoid(final int channel) {
        super(channel);
    }

    /**
     * Construct the solenoid.
     * 
     * @param modulenumber The module number.
     * @param channel      The channel to use.
     */
    public WSolenoid(final int modulenumber, final int channel) {
        super(modulenumber, channel);
    }
}
