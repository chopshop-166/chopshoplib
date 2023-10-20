package com.chopshop166.chopshoplib.sensors;

import com.ctre.phoenix.sensors.CANCoder;

/**
 * An Encoder on the CAN bus.
 */
public class CtreEncoder implements IEncoder {

    /** Reference to the base encoder. */
    private final CANCoder enc;

    /**
     * Construct the encoder from the raw object.
     * 
     * @param enc The CANCoder to connect to.
     */
    public CtreEncoder(final CANCoder enc) {
        this.enc = enc;
    }

    /**
     * Construct the encoder from the device ID.
     * 
     * @param deviceId The device ID on the CAN bus.
     */
    public CtreEncoder(final int deviceId) {
        this(new CANCoder(deviceId));
    }

    @Override
    public double getDistance() {
        // This encoder can't record distance
        return 0;
    }

    @Override
    public double getRate() {
        return this.enc.getVelocity();
    }

    @Override
    public double getAbsolutePosition() {
        return this.enc.getAbsolutePosition();
    }

    @Override
    public void reset() {
        // No no, we don't do that here.
    }
}
