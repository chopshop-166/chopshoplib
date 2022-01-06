package com.chopshop166.chopshoplib.digital;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DigitalOutput;

/**
 * A mock {@link DigitalOutput} that can be sent to the dashboard.
 */
public class MockDigitalOutput implements IDigitalOutput {

    /** The last set value. */
    private boolean value;
    /** The last set pwm rate. */
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