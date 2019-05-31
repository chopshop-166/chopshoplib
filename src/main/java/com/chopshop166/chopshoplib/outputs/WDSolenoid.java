package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * An instance of {@link DoubleSolenoid} that can be mocked.
 */
public final class WDSolenoid extends DoubleSolenoid implements IDSolenoid {

    public WDSolenoid(final int forwardChannel, final int reverseChannel) {
        super(forwardChannel, reverseChannel);
    }

    public WDSolenoid(final int modulenumber, final int forwardChannel, final int reverseChannel) {
        super(modulenumber, forwardChannel, reverseChannel);
    }
}
