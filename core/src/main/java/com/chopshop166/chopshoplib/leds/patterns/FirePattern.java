package com.chopshop166.chopshoplib.leds.patterns;

import java.util.Random;
import com.chopshop166.chopshoplib.leds.Pattern;
import com.chopshop166.chopshoplib.leds.SegmentBuffer;

import edu.wpi.first.wpilibj.util.Color;

/** F-f-f-f-flames. Flames, on the side of my face. */
public class FirePattern extends Pattern {
    /** The heatmap. */
    private final byte[] heat;
    /** The intensity of the flames. */
    private final int intensity;
    /** The RNG. */
    private final Random rand = new Random();

    /**
     * Constructor.
     * 
     * @param length The length of the fire.
     */
    public FirePattern(final int length) {
        this(length, 30);
    }

    /**
     * Constructor.
     * 
     * @param length The length of the fire.
     * @param intensity The intensity of the fire.
     */
    public FirePattern(final int length, final int intensity) {
        super();
        this.heat = new byte[length];
        this.intensity = intensity;
    }

    @Override
    public void initialize(final SegmentBuffer buffer) {
        // Do nothing
    }

    @Override
    public void update(final SegmentBuffer buffer) {
        this.calculateFire(this.intensity);
        for (int i = 1; i < this.heat.length; i++) {
            buffer.set(i, this.heatToColor(this.heat[i]));
        }
    }

    /**
     * Map a heat color to a given value.
     * 
     * @param h The heat value.
     * @return A color.
     */
    protected Color heatToColor(final byte h) {
        // Scale 'heat' down from 0-255 to 0-191
        final byte t192 = (byte) Math.round((h / 255.0) * 191);

        // calculate ramp up from
        byte heatramp = (byte) (t192 & 0x3F); // 0..63
        heatramp <<= 2; // scale up to 0..252

        // figure out which third of the spectrum we're in:
        if ((t192 & 0xFF) > 0x80) { // hottest
            return new Color(255, 255, heatramp);
        } else if ((t192 & 0xFF) > 0x40) { // middle
            return new Color(255, heatramp, 0);
        } else { // coolest
            return new Color(heatramp, 0, 0);
        }
    }

    private void calculateFire(final int sparks) {
        // Cool down each cell a little
        for (int i = 1; i < this.heat.length; i++) {
            final int cooldown = this.rand.nextInt(12);

            if (cooldown > this.heat[i]) {
                this.heat[i] = 0;
            } else {
                this.heat[i] = (byte) (this.heat[i] - cooldown);
            }
        }

        // Heat from each cell drifts up and diffuses slightly
        for (int k = this.heat.length - 1; k >= 2; k--) {
            this.heat[k] = (byte) ((this.heat[k - 1] + this.heat[k - 2] + this.heat[k - 2]) / 3);
        }

        // Randomly ignite new sparks near bottom of the flame
        if (this.rand.nextInt(255) < sparks) {
            final int y = this.rand.nextInt(7);
            this.heat[y] = (byte) (this.heat[y] + this.rand.nextInt(64));
        }
    }

    @Override
    public String toString() {
        return "FirePattern()";
    }
}
