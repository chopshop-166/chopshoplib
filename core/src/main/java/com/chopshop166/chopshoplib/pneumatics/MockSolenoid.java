package com.chopshop166.chopshoplib.pneumatics;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * An instance of {@link Solenoid} that can be controlled via the dashboard.
 * 
 * It does not correlate with any real hardware.
 */
public final class MockSolenoid implements ISolenoid {

    /** Whether it's triggered or not. */
    private boolean value;

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Solenoid");
        builder.setActuator(true);
        builder.setSafeState(() -> set(false));
        builder.addBooleanProperty("Value", this::get, this::set);
    }

    @Override
    public void set(final boolean value) {
        this.value = value;
    }

    @Override
    public boolean get() {
        return value;
    }

    @Override
    public boolean isDisabled() {
        return false;
    }

    @Override
    public void setPulseDuration(final double durationSeconds) {
        // Not implemented
    }

    @Override
    public void startPulse() {
        // Not implemented
    }

    @Override
    public void close() throws Exception {
        // Do nothing
    }

}
