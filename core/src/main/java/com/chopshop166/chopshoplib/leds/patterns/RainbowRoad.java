package com.chopshop166.chopshoplib.leds.patterns;

import com.chopshop166.chopshoplib.leds.Pattern;
import com.chopshop166.chopshoplib.leds.SegmentBuffer;

import edu.wpi.first.wpilibj.util.Color;

/** Double rainbow all the way across the sky! */
public class RainbowRoad extends Pattern {
    /** The color of the first LED in the chain. */
    private int rainbowFirstPixelHue;

    @Override
    public void initialize(final SegmentBuffer buffer) {
        this.rainbowFirstPixelHue = 0;
    }

    @Override
    public void update(final SegmentBuffer segment) {
        for (var i = 0; i < segment.getLength(); i++) {
            final int hue = (this.rainbowFirstPixelHue + (i * 180 / segment.getLength())) % 180;
            // Segment also allows setting individual lights by HSV, Color, or RGB
            segment.set(i, Color.fromHSV(hue, 255, 128));
        }
        this.rainbowFirstPixelHue += 177;
        this.rainbowFirstPixelHue %= 180;
    }

    @Override
    public String toString() {
        return "RainbowRoad()";
    }
}
