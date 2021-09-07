package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.Sendable;

/**
 * Interface to write digital outputs.
 */
public interface IDigitalOutput extends Sendable, AutoCloseable {

    /**
     * Set the value of a digital output.
     *
     * @param value true is on, off is false.
     */
    void set(boolean value);

    /**
     * Gets the value being output from the Digital Output.
     *
     * @return the state of the digital output.
     */
    boolean get();

    /**
     * Generate a single pulse. There can only be a single pulse going at any time.
     *
     * @param pulseLength The length of the pulse.
     */
    void pulse(final double pulseLength);

    /**
     * Determine if the pulse is still going. Determine if a previously started
     * pulse is still going.
     *
     * @return true if pulsing
     */
    boolean isPulsing();

    /**
     * Change the PWM frequency of the PWM output on a Digital Output line.
     *
     * <p>
     * The valid range is from 0.6 Hz to 19 kHz. The frequency resolution is
     * logarithmic.
     *
     * <p>
     * There is only one PWM frequency for all channels.
     *
     * @param rate The frequency to output all digital output PWM signals.
     */
    void setPWMRate(double rate);

    /**
     * Enable a PWM Output on this line.
     *
     * <p>
     * Allocate one of the 6 DO PWM generator resources.
     *
     * <p>
     * Supply the initial duty-cycle to output so as to avoid a glitch when first
     * starting.
     *
     * <p>
     * The resolution of the duty cycle is 8-bit for low frequencies (1kHz or less)
     * but is reduced the higher the frequency of the PWM signal is.
     *
     * @param initialDutyCycle The duty-cycle to start generating. [0..1]
     */
    void enablePWM(double initialDutyCycle);

    /**
     * Change this line from a PWM output back to a static Digital Output line.
     *
     * <p>
     * Free up one of the 6 DO PWM generator resources that were in use.
     */
    void disablePWM();

    /**
     * Change the duty-cycle that is being generated on the line.
     *
     * <p>
     * The resolution of the duty cycle is 8-bit for low frequencies (1kHz or less)
     * but is reduced the higher the frequency of the PWM signal is.
     *
     * @param dutyCycle The duty-cycle to change to. [0..1]
     */
    void updateDutyCycle(double dutyCycle);

    /**
     * Get the PWM rate (duty cycle) of the output.
     * 
     * @return The rate [0..1].
     */
    double getPWMRate();

}
