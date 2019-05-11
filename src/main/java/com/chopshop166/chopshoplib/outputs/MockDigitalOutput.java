package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * A mock {@link DigitalOutput} that can be sent to the dashboard.
 */
public class MockDigitalOutput extends SendableBase implements DigitalOutputInterface {

    private boolean value;
    private double rate;

    /**
     * Create an instance of a digital output.
     * <p>
     * Create an instance of a digital output given a channel.
     */
    public MockDigitalOutput() {
    }

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
    public void set(boolean value) {
        this.value = value;
    }

    @Override
    public boolean get() {
        return value;
    }

    @Override
    public void pulse(double pulseLength) {

    }

    @Override
    public boolean isPulsing() {
        return false;
    }

    @Override
    public void setPWMRate(double rate) {
        this.rate = rate;
    }

    @Override
    public void disablePWM() {
        this.rate = 0.0;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Digital Output");
        builder.addBooleanProperty("Value", this::get, this::set);
    }
}