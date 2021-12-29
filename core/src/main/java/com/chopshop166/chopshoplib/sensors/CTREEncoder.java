package com.chopshop166.chopshoplib.sensors;

import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * An Encoder on the CAN bus.
 */
public class CTREEncoder implements IEncoder {

    /** Reference to the base encoder. */
    private final CANCoder enc;

    /**
     * Construct the encoder from the raw object.
     * 
     * @param enc The CANCoder to connect to.
     */
    public CTREEncoder(final CANCoder enc) {
        this.enc = enc;
    }

    /**
     * Construct the encoder from the device ID.
     * 
     * @param deviceId The device ID on the CAN bus.
     */
    public CTREEncoder(final int deviceId) {
        this(new CANCoder(deviceId));
    }

    @Override
    public double getDistance() {
        // This encoder can't record distance
        return 0;
    }

    @Override
    public double getRate() {
        return enc.getVelocity();
    }

    @Override
    public double getAbsolutePosition() {
        return enc.getAbsolutePosition();
    }

    @Override
    public void reset() {
        // No no, we don't do that here.
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Encoder");
        builder.addDoubleProperty("Speed", this::getRate, null);
        builder.addDoubleProperty("Distance", this::getDistance, null);
        builder.addDoubleProperty("Distance per Tick", () -> 0, null);
    }
}