package com.chopshop166.chopshoplib.pneumatics;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * An instance of {@link DoubleSolenoid} that can be wrapped.
 */
public final class MockDSolenoid implements IDSolenoid {

    /** The last commanded value. */
    private Value value = Value.kOff;

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Double Solenoid");
        builder.setActuator(true);
        builder.setSafeState(() -> this.set(Value.kOff));
        builder.addStringProperty("Value", () -> this.get().name().substring(1), value -> {
            if ("Forward".equals(value)) {
                this.set(Value.kForward);
            } else if ("Reverse".equals(value)) {
                this.set(Value.kReverse);
            } else {
                this.set(Value.kOff);
            }
        });
    }

    @Override
    public void set(final Value value) {
        this.value = value;
    }

    @Override
    public Value get() {
        return this.value;
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
