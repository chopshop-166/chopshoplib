package com.chopshop166.chopshoplib.pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * A Double Solenoid attached to a CTRE PCM.
 */
public class CtreDSolenoid extends DoubleSolenoid implements IDSolenoid {

    /**
     * Construct a REV Double Solenoid.
     * 
     * @param forwardChannel The forward trigger channel.
     * @param reverseChannel The reverse trigger channel.
     */
    public CtreDSolenoid(final int forwardChannel, final int reverseChannel) {
        super(PneumaticsModuleType.CTREPCM, forwardChannel, reverseChannel);
    }

    /**
     * Construct a REV Double Solenoid.
     * 
     * @param modulenumber The module to use.
     * @param forwardChannel The forward trigger channel.
     * @param reverseChannel The reverse trigger channel.
     */
    public CtreDSolenoid(final int modulenumber, final int forwardChannel,
            final int reverseChannel) {
        super(modulenumber, PneumaticsModuleType.CTREPCM, forwardChannel, reverseChannel);
    }
}
