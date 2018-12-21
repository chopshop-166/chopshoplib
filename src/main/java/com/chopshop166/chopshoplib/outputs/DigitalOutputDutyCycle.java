package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.DigitalOutput;

public class DigitalOutputDutyCycle extends DigitalOutput {

    private double mRate;

    public DigitalOutputDutyCycle(final int channel) {
        super(channel);
    }

    @Override
    public void updateDutyCycle(final double rate) {
        mRate = rate;
        super.updateDutyCycle(rate);
    }

    @Override
    public void enablePWM(final double initialDutyCycle) {
        mRate = initialDutyCycle;
        super.enablePWM(initialDutyCycle);
    }

    public double getPWMRate() {
        return mRate;
    }
}