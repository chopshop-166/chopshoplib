package com.chopshop166.chopshoplib.sensors.gyro;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * Gyro Base wrapper for the Pigeon IMU
 */
public class PigeonGyro implements SmartGyro {

    /** The wrapped object. */
    final private PigeonIMU gyro;
    /** Boolean to control inverted output */
    private boolean inverted;

    /**
     * Create the wrapper.
     * 
     * @param gyro The object to wrap.
     */
    public PigeonGyro(final PigeonIMU gyro) {
        super();
        this.gyro = gyro;
    }

    /**
     * Create the wrapper.
     * 
     * @param talon The Talon that the gyro is attached to.
     */
    public PigeonGyro(final TalonSRX talon) {
        this(new PigeonIMU(talon));
    }

    /**
     * Get the wrapped object.
     * 
     * @return The wrapped class.
     */
    public PigeonIMU getRaw() {
        return gyro;
    }

    /**
     * Inverts the angle and rate of the Pigeon
     *
     * @param isInverted The state of inversion, true is inverted.
     */
    public void setInverted(final boolean isInverted) {
        this.inverted = isInverted;
    }

    @Override
    public void close() throws Exception {
        // NoOp
    }

    /**
     * Sets the gyro's heading back to zero
     */
    @Override
    public void reset() {
        gyro.setFusedHeading(0);
    }

    /**
     * @return The rate of rotation of the gyro.
     */
    @Override
    public double getRate() {
        final double[] xyz = new double[3];
        gyro.getRawGyro(xyz);
        return inverted ? -xyz[2] : xyz[2];
    }

    /**
     * @return The current angle of the gyro
     */
    @Override
    public double getAngle() {
        return inverted ? -gyro.getFusedHeading() : gyro.getFusedHeading();
    }

    @Override
    public void calibrate() {
        // NoOp
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Gyro");
        builder.addDoubleProperty("Value", this::getAngle, null);
        builder.addDoubleProperty("Rate", this::getRate, null);
    }
}