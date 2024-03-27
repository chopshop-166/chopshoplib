package com.chopshop166.chopshoplib.maps;

import com.ctre.phoenix.led.CANdle;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

/** A map for an LED subsystem that uses a CANdle. */
public class CANdleLedMap extends LedMapBase {
    /** The wpilib LED strip object. */
    public final CANdle candle;

    /**
     * Constructor.
     * 
     * @param ledBufferLength The length of the LEDs.
     * @param ledPort The CANdle port.
     */
    public CANdleLedMap(final int ledBufferLength, final int ledPort) {
        super(ledBufferLength);
        this.candle = new CANdle(ledPort);
    }


    @Override
    public void setData(final AddressableLEDBuffer buf) {
        for (int i = 0; i < buf.getLength(); i++) {
            final Color c = buf.getLED(i);
            this.candle.setLEDs((int) (c.red * 255), (int) (c.green * 255), (int) (c.blue * 255),
                    255, i, 1);
        }
    }
}
