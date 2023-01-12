package com.chopshop166.chopshoplib.pneumatics;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * A Solenoid attached to a CTRE PCM.
 */
public final class CtreSolenoid extends Solenoid implements ISolenoid {

    /**
     * Construct the solenoid.
     * 
     * @param channel The channel to use.
     */
    public CtreSolenoid(final int channel) {
        super(PneumaticsModuleType.CTREPCM, channel);
    }

    /**
     * Construct the solenoid.
     * 
     * @param modulenumber The module number.
     * @param channel The channel to use.
     */
    public CtreSolenoid(final int modulenumber, final int channel) {
        super(modulenumber, PneumaticsModuleType.CTREPCM, channel);
    }
}
