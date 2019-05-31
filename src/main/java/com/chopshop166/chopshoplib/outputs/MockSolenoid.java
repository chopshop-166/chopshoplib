package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * An instance of {@link Solenoid} that can be controlled via the dashboard.
 * 
 * It does not correlate with any real hardware.
 */
public final class MockSolenoid extends SendableBase implements ISolenoid {

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
    public boolean isBlackListed() {
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

}
