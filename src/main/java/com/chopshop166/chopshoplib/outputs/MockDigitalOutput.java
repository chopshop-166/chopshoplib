package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * A mock {@link DigitalOutput} that can be sent to the dashboard.
 */
public class MockDigitalOutput implements IDigitalOutput, Sendable {

    private boolean value;
    private double rate;

    @Override
    public void updateDutyCycle(final double rate) {
        this.rate = rate;
    }

    @Override
    public void enablePWM(final double initialDutyCycle) {
        this.rate = initialDutyCycle;
    }

    @Override
    public double getPWMRate() {
        return rate;
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
    public void pulse(final double pulseLength) {
        // This does nothing
    }

    @Override
    public boolean isPulsing() {
        return false;
    }

    @Override
    public void setPWMRate(final double rate) {
        this.rate = rate;
    }

    @Override
    public void disablePWM() {
        this.rate = 0.0;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Digital Output");
        builder.addBooleanProperty("Value", this::get, this::set);
    }

    @Override
    public void close() throws Exception {
        // Do nothing (nothing to close)
    }
}