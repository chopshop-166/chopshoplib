package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * An instance of {@link DoubleSolenoid} that can be wrapped.
 */
public final class MockDSolenoid implements IDSolenoid {

    /** The last commanded value. */
    private Value value;

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Double Solenoid");
        builder.setActuator(true);
        builder.setSafeState(() -> set(Value.kOff));
        builder.addStringProperty("Value", () -> get().name().substring(1), value -> {
            if ("Forward".equals(value)) {
                set(Value.kForward);
            } else if ("Reverse".equals(value)) {
                set(Value.kReverse);
            } else {
                set(Value.kOff);
            }
        });
    }

    @Override
    public void set(final Value value) {
        this.value = value;
    }

    @Override
    public Value get() {
        return value;
    }

    @Override
    public boolean isFwdSolenoidDisabled() {
        return false;
    }

    @Override
    public boolean isRevSolenoidDisabled() {
        return false;
    }

    @Override
    public void close() throws Exception {
        // Do nothing
    }

}
