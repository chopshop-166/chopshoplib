package com.chopshop166.chopshoplib.sensors;

import com.ctre.phoenix6.hardware.CANcoder;

/**
 * An Encoder on the CAN bus.
 */
public class CtreEncoder implements IEncoder {

    /** Reference to the base encoder. */
    private final CANcoder enc;

    /**
     * Construct the encoder from the raw object.
     * 
     * @param enc The CANCoder to connect to.
     */
    public CtreEncoder(final CANcoder enc) {
        this.enc = enc;
    }

    /**
     * Construct the encoder from the device ID.
     * 
     * @param deviceId The device ID on the CAN bus.
     */
    public CtreEncoder(final int deviceId) {
        this(new CANcoder(deviceId));
    }

    @Override
    public double getDistance() {
        // This encoder can't record distance
        return 0;
    }

    @Override
    public double getRate() {
        return this.enc.getVelocity().getValueAsDouble();
    }

    @Override
    public double getAbsolutePosition() {
        return this.enc.getAbsolutePosition().getValueAsDouble();
    }

    @Override
    public void reset() {
        // No no, we don't do that here.
    }
}
