package com.chopshop166.chopshoplib.sensors;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * uses DigitalInputSource interface to create a invertable input source (cuz !
 * is boring)
 */
public class InvertDigitalInput extends DigitalInput implements DigitalInputSource {

/**
 * creates inverted boolean to be used for logic later
 */
     * 
     private boolean inverted;

/**
 * allows input to change the inverted variable
 */
    public void setInverted(boolean invertValue) {
        inverted = invertValue;
    }

    /**
     * allows to check for inversion
     */
    public boolean getInverted() {
        return inverted;
    }

    /**
     * creates our input via the source interface
     */
    public InvertDigitalInput(int channel) {
        super(channel);
    }

    /**
     * Uses logic to use the inverted boolean to invert the returned value
     */
    @Override
    public boolean getAsBoolean() {
        if (inverted == true) {
            return !super.get();
        } else
            return super.get();
    }
}
