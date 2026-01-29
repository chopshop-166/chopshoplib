package com.chopshop166.chopshoplib.maps;

import com.ctre.phoenix6.controls.SolidColor;
import com.ctre.phoenix6.hardware.CANdle;
import com.ctre.phoenix6.signals.RGBWColor;
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
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void setData(final AddressableLEDBuffer buf) {
        for (int i = 0; i < buf.getLength(); i++) {
            final Color c = buf.getLED(i);
            this.candle.setControl(new SolidColor(i, i).withColor(new RGBWColor(c)));
        }
    }
}
