package com.chopshop166.chopshoplib.pneumatics;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * A Solenoid attached to a REV Pneumatics Hub.
 */
public final class RevSolenoid extends Solenoid implements ISolenoid {

    /**
     * Construct the solenoid.
     * 
     * @param channel The channel to use.
     */
    public RevSolenoid(final int channel) {
        super(PneumaticsModuleType.REVPH, channel);
    }

    /**
     * Construct the solenoid.
     * 
     * @param modulenumber The module number.
     * @param channel      The channel to use.
     */
    public RevSolenoid(final int modulenumber, final int channel) {
        super(modulenumber, PneumaticsModuleType.REVPH, channel);
    }
}
