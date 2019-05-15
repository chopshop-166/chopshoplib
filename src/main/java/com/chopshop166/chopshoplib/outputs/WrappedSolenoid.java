package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * An instance of {@link Solenoid} that can be mocked.
 */
public final class WrappedSolenoid extends Solenoid implements SolenoidInterface {

    public WrappedSolenoid(final int channel) {
        super(channel);
    }

    public WrappedSolenoid(final int modulenumber, final int channel) {
        super(modulenumber, channel);
    }
}
