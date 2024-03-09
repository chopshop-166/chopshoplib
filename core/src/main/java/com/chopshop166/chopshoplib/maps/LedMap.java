package com.chopshop166.chopshoplib.maps;

import com.chopshop166.chopshoplib.leds.ColorFormat;
import com.chopshop166.chopshoplib.leds.LEDStripBuffer;
import edu.wpi.first.wpilibj.AddressableLED;

/** A subsystem map */
public class LedMap {
    /** The wpilib LED strip object. */
    public final AddressableLED led;
    /** The segment buffer. */
    public final LEDStripBuffer ledBuffer;
    /** The format of the LEDs */
    public final ColorFormat format;

    /** Constructor. */
    public LedMap() {
        this(0, 0);
    }


    /**
     * Constructor.
     * 
     * @param ledPort The port the LEDs are plugged into.
     * @param ledBufferLength The length of the LEDs.
     */
    public LedMap(final int ledPort, final int ledBufferLength) {
        this(ledPort, ledBufferLength, ColorFormat.RGB);
    }

    /**
     * Constructor.
     * 
     * @param ledPort The port the LEDs are plugged into.
     * @param ledBufferLength The length of the LEDs.
     * @param format The format of the leds RGB,GRB
     */
    public LedMap(final int ledPort, final int ledBufferLength, final ColorFormat format) {
        this.led = new AddressableLED(ledPort);
        this.ledBuffer = new LEDStripBuffer(ledBufferLength, format);
        this.format = format;
    }
}
