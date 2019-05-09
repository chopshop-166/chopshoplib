package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * An instance of {@link DoubleSolenoid} that can be mocked.
 */
public final class WrappedDoubleSolenoid extends DoubleSolenoid implements DoubleSolenoidInterface {

    public WrappedDoubleSolenoid(int forwardChannel, int reverseChannel) {
        super(forwardChannel, reverseChannel);
    }

    public WrappedDoubleSolenoid(int modulenumber, int forwardChannel, int reverseChannel) {
        super(modulenumber, forwardChannel, reverseChannel);
    }
}
