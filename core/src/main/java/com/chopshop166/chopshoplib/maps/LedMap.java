package com.chopshop166.chopshoplib.maps;

import com.chopshop166.chopshoplib.leds.LEDStripBuffer;
import edu.wpi.first.wpilibj.AddressableLED;

/** A subsystem map */
public class LedMap {
    /** The wpilib LED strip object. */
    public final AddressableLED led;
    /** The segment buffer. */
    public final LEDStripBuffer ledBuffer;

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
        this.led = new AddressableLED(ledPort);
        this.ledBuffer = new LEDStripBuffer(ledBufferLength);
    }
}
