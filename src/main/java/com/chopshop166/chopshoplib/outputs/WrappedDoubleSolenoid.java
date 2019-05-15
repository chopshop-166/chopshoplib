package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * An instance of {@link DoubleSolenoid} that can be mocked.
 */
public final class WrappedDoubleSolenoid extends DoubleSolenoid implements DoubleSolenoidInterface {

    public WrappedDoubleSolenoid(final int forwardChannel, final int reverseChannel) {
        super(forwardChannel, reverseChannel);
    }

    public WrappedDoubleSolenoid(final int modulenumber, final int forwardChannel, final int reverseChannel) {
        super(modulenumber, forwardChannel, reverseChannel);
    }
}
