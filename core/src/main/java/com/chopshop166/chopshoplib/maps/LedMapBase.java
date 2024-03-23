package com.chopshop166.chopshoplib.maps;

import com.chopshop166.chopshoplib.leds.LEDStripBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

/** A map for an LED subsystem. */
public abstract class LedMapBase {
    /** The segment buffer. */
    public final LEDStripBuffer ledBuffer;

    /** Constructor. */
    public LedMapBase() {
        this(0);
    }

    /**
     * Constructor.
     * 
     * @param ledBufferLength The length of the LEDs.
     */
    public LedMapBase(final int ledBufferLength) {
        this.ledBuffer = new LEDStripBuffer(ledBufferLength);
    }

    /**
     * Set the data on the hardware.
     * 
     * @param buf The color buffer.
     */
    public abstract void setData(AddressableLEDBuffer buf);
}
