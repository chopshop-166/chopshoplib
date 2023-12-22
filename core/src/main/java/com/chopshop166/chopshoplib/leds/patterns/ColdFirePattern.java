package com.chopshop166.chopshoplib.leds.patterns;

import edu.wpi.first.wpilibj.util.Color;

/** Cold fire pattern. */
public class ColdFirePattern extends FirePattern {

    /**
     * Constructor.
     * 
     * @param length The length of the fire.
     */
    public ColdFirePattern(final int length) {
        super(length);
    }

    /**
     * Constructor.
     * 
     * @param length The length of the fire.
     * @param intensity The intensity of the fire.
     */
    public ColdFirePattern(final int length, final int intensity) {
        super(length, intensity);
    }

    @Override
    protected Color heatToColor(final byte h) {
        // Scale 'heat' down from 0-255 to 0-191
        final byte t192 = (byte) Math.round((h / 255.0) * 191);

        // calculate ramp up from
        byte heatramp = (byte) (t192 & 0x3F); // 0..63
        heatramp <<= 2; // scale up to 0..252

        // figure out which third of the spectrum we're in:
        if ((t192 & 0xFF) > 0x80) { // hottest
            return new Color(heatramp, 255, 255);
        } else if ((t192 & 0xFF) > 0x40) { // middle
            return new Color(0, heatramp, 255);
        } else { // coolest
            return new Color(0, 0, heatramp);
        }
    }

    @Override
    public String toString() {
        return "ColdFirePattern()";
    }
}
