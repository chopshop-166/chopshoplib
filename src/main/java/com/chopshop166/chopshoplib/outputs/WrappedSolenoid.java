package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * An instance of {@link Solenoid} that can be mocked.
 */
public final class WrappedSolenoid extends Solenoid implements SolenoidInterface {

    public WrappedSolenoid(int channel) {
        super(channel);
    }

    public WrappedSolenoid(int modulenumber, int channel) {
        super(modulenumber, channel);
    }
}
