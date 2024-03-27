package com.chopshop166.chopshoplib.maps;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;

/** A map for an LED subsystem that doesn't do anything. */
public class MockLedMap extends LedMapBase {

    /** Constructor. */
    public MockLedMap() {
        super(0);
    }

    @Override
    public void setData(final AddressableLEDBuffer buf) {
        // Do nothing, successfully
    }
}
