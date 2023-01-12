package com.chopshop166.chopshoplib.pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * A Double Solenoid attached to a REV Pneumatics Hub.
 */
public class RevDSolenoid extends DoubleSolenoid implements IDSolenoid {

    /**
     * Construct a REV Double Solenoid.
     * 
     * @param forwardChannel The forward trigger channel.
     * @param reverseChannel The reverse trigger channel.
     */
    public RevDSolenoid(final int forwardChannel, final int reverseChannel) {
        super(PneumaticsModuleType.REVPH, forwardChannel, reverseChannel);
    }

    /**
     * Construct a REV Double Solenoid.
     * 
     * @param modulenumber The module to use.
     * @param forwardChannel The forward trigger channel.
     * @param reverseChannel The reverse trigger channel.
     */
    public RevDSolenoid(final int modulenumber, final int forwardChannel,
            final int reverseChannel) {
        super(modulenumber, PneumaticsModuleType.REVPH, forwardChannel, reverseChannel);
    }
}
