package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.DigitalOutput;

/**
 * A {@link DigitalOutput} that records its PWM rate.
 */
public class DigitalOutputDutyCycle extends DigitalOutput {

    private double mRate;

    /**
     * Create an instance of a digital output.
     * <p>
     * Create an instance of a digital output given a channel.
     *
     * @param channel the DIO channel to use for the digital output. 0-9 are
     *                on-board, 10-25 are on the MXP
     */
    public DigitalOutputDutyCycle(final int channel) {
        super(channel);
    }

    /**
     * Update the duty cycle and record for future use.
     * 
     * @see DigitalOutput#updateDutyCycle(double)
     */
    @Override
    public void updateDutyCycle(final double rate) {
        mRate = rate;
        super.updateDutyCycle(rate);
    }

    /**
     * Enable PWM usage and record the initial duty cycle.
     * 
     * @see DigitalOutput#updateDutyCycle(double)
     */
    @Override
    public void enablePWM(final double initialDutyCycle) {
        mRate = initialDutyCycle;
        super.enablePWM(initialDutyCycle);
    }

    /**
     * Get the PWM rate (duty cycle) of the output.
     * 
     * @return The rate [0..1].
     */
    public double getPWMRate() {
        return mRate;
    }
}