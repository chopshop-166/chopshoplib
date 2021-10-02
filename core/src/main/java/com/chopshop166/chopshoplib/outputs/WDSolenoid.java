package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * An instance of {@link DoubleSolenoid} that can be mocked.
 */
public final class WDSolenoid extends DoubleSolenoid implements IDSolenoid {

    /**
     * Construct the Double Solenoid.
     * 
     * @param forwardChannel The forward trigger channel.
     * @param reverseChannel The reverse trigger channel.
     */
    public WDSolenoid(final int forwardChannel, final int reverseChannel) {
        super(forwardChannel, reverseChannel);
    }

    /**
     * Construct the Double Solenoid.
     * 
     * @param modulenumber   The module to use.
     * @param forwardChannel The forward trigger channel.
     * @param reverseChannel The reverse trigger channel.
     */
    public WDSolenoid(final int modulenumber, final int forwardChannel, final int reverseChannel) {
        super(modulenumber, forwardChannel, reverseChannel);
    }
}
