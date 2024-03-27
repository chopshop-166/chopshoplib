package com.chopshop166.chopshoplib.maps;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

/** A map for an LED subsystem that uses WPIlib's LEDs. */
public class WPILedMap extends LedMapBase {
    /** The wpilib LED strip object. */
    public final AddressableLED led;

    /** Constructor. */
    public WPILedMap() {
        this(0, 0);
    }

    /**
     * Constructor.
     * 
     * @param ledBufferLength The length of the LEDs.
     * @param ledPort The port the LEDs are plugged into.
     */
    public WPILedMap(final int ledBufferLength, final int ledPort) {
        super(ledBufferLength);
        this.led = new AddressableLED(ledPort);

        this.led.setLength(this.ledBuffer.getLength());
        this.led.start();
    }

    @Override
    public void setData(final AddressableLEDBuffer buf) {
        this.led.setData(buf);
    }
}
