package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Invert Digital Input
 * <p>
 * creates boolean that is used to create a Digital Input {@link DigitalInput}
 * that can be inverted based on the value passed into the boolean, adds
 * functions that send and retrieve the inverted boolean
 * 
 * @author Andrew Martin
 * @since 2020-01-20
 */
public class InvertDigitalInput extends DigitalInput implements DigitalInputSource {

    /**
     * creates inverted boolean to be used for logic later
     */
    private boolean inverted;

    /**
     * creates our input via the source interface
     */
    public InvertDigitalInput(int channel) {
        super(channel);
    }

    /**
     * allows to check for inversion using the boolean created above
     */
    public boolean getInverted() {
        return inverted;
    }

    /**
     * allows input to change the inverted variable
     */
    public void setInverted(boolean invertValue) {
        inverted = invertValue;
    }

    /**
     * Uses exclusive or to invert the returned value based on the inverted variable
     */
    public boolean get() {
        return inverted ^ super.get();
    }

    public boolean getAsBoolean() {
        return get();
    }
}
