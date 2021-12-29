package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * An instance of {@link Solenoid} that can be mocked.
 */
public final class WSolenoid extends Solenoid implements ISolenoid {

    /**
     * Construct the solenoid.
     * 
     * @param type    The pneumatics module type.
     * @param channel The channel to use.
     */
    public WSolenoid(final PneumaticsModuleType type, final int channel) {
        super(type, channel);
    }

    /**
     * Construct the solenoid.
     * 
     * @param modulenumber The module number.
     * @param type         The pneumatics module type.
     * @param channel      The channel to use.
     */
    public WSolenoid(final int modulenumber, final PneumaticsModuleType type, final int channel) {
        super(modulenumber, type, channel);
    }
}
