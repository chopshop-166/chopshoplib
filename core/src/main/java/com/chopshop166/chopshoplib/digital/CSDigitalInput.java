package com.chopshop166.chopshoplib.digital;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Invertable Digital Input
 * 
 * @author Andrew Martin
 * @since 2020-01-20
 */
public class CSDigitalInput extends DigitalInput implements DigitalInputSource {

    /**
     * True if the reading should be inverted.
     */
    private boolean inverted;

    /**
     * Creates an input via the source interface.
     * 
     * @param channel The Digital I/O Channel to use.
     */
    public CSDigitalInput(final int channel) {
        super(channel);
    }

    /**
     * Allows to check for inversion using the boolean created above.
     * 
     * @return True if the input is inverted.
     */
    public boolean getInverted() {
        return inverted;
    }

    /**
     * Allows input to change the inverted variable.
     * 
     * @param invertValue True if the input should be inverted.
     */
    public void setInverted(final boolean invertValue) {
        inverted = invertValue;
    }

    /**
     * Uses exclusive or to invert the returned value based on the inverted
     * variable.
     */
    @Override
    public boolean get() {
        return inverted ^ super.get();
    }

    @Override
    public boolean getAsBoolean() {
        return get();
    }
}
