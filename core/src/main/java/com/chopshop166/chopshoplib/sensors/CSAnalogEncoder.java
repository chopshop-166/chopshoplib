package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.AnalogEncoder;

// Wrapper class for AnalogEncoder implementing IAbsolutePosition interface
public class CSAnalogEncoder extends AnalogEncoder implements IAbsolutePosition {
    private double offset;

    // Constructor that initializes the AnalogEncoder with the specified channel
    public CSAnalogEncoder(final int channel) {
        super(channel);
    }

    public CSAnalogEncoder(final int channel, final double distancePerRotation) {
        super(channel);
        setDistancePerRotation(distancePerRotation);
    }

    public CSAnalogEncoder(final int channel, final double distancePerRotation,
            final double offset) {
        super(channel);
        setDistancePerRotation(distancePerRotation);
        setOffset(offset);
    }

    // Override method to get the absolute position from the encoder
    @Override
    public double getAbsolutePosition() {
        return get() + offset;
    }

    // Method to set the offset
    public void setOffset(double offset) {
        this.offset = offset;
    }
}
