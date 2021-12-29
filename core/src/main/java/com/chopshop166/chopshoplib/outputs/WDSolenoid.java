package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * An instance of {@link DoubleSolenoid} that can be mocked.
 */
public final class WDSolenoid extends DoubleSolenoid implements IDSolenoid {

    /**
     * Construct the Double Solenoid.
     * 
     * @param type           The pneumatics module type.
     * @param forwardChannel The forward trigger channel.
     * @param reverseChannel The reverse trigger channel.
     */
    public WDSolenoid(final PneumaticsModuleType type, final int forwardChannel, final int reverseChannel) {
        super(type, forwardChannel, reverseChannel);
    }

    /**
     * Construct the Double Solenoid.
     * 
     * @param modulenumber   The module to use.
     * @param type           The pneumatics module type.
     * @param forwardChannel The forward trigger channel.
     * @param reverseChannel The reverse trigger channel.
     */
    public WDSolenoid(final int modulenumber, final PneumaticsModuleType type,
            final int forwardChannel, final int reverseChannel) {
        super(modulenumber, type, forwardChannel, reverseChannel);
    }
}
