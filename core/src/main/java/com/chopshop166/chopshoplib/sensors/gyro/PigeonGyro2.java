package com.chopshop166.chopshoplib.sensors.gyro;

import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * Gyro Base wrapper for the Pigeon IMU
 */
public class PigeonGyro2 implements SmartGyro {

    /** The wrapped object. */
    final private Pigeon2 gyro;
    /** Boolean to control inverted output. */
    private boolean inverted;

    /**
     * Create the wrapper.
     *
     * @param gyro The object to wrap.
     */
    public PigeonGyro2(final Pigeon2 gyro) {
        super();
        this.gyro = gyro;

        // Automatically invert if the gyro is upside-down
        final short accelerometerXYZ[] = new short[3];
        this.gyro.getBiasedAccelerometer(accelerometerXYZ);
        if (accelerometerXYZ[2] < 0) {
            this.inverted = true;
        }
    }

    /**
     * Create the wrapper.
     *
     * @param deviceNumber The CAN ID of the Pigeon 2.
     */
    public PigeonGyro2(final int deviceNumber) {
        this(new Pigeon2(deviceNumber));
    }


    /**
     * Get the wrapped object.
     *
     * @return The wrapped class.
     */
    public Pigeon2 getRaw() {
        return this.gyro;
    }

    /**
     * Inverts the angle and rate of the Pigeon.
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
        this.gyro.setYaw(0);
    }

    /**
     * @return The rate of rotation of the gyro.
     */
    @Override
    public double getRate() {
        final double[] xyz = new double[3];
        this.gyro.getRawGyro(xyz);
        return this.inverted ? -xyz[2] : xyz[2];
    }

    /**
     * @return The current angle of the gyro
     */
    @Override
    public double getAngle() {
        return this.inverted ? -this.gyro.getYaw() : this.gyro.getYaw();
    }

    @Override
    public void setAngle(final double angleDeg) {
        this.gyro.setYaw(angleDeg);
    }

    @Override
    public void calibrate() {
        // NoOp
    }

    @Override
    public Rotation3d getRotation3d() {
        final double[] yprDeg = new double[3];
        this.gyro.getYawPitchRoll(yprDeg);
        return new Rotation3d(yprDeg[2], yprDeg[1], yprDeg[0]);
    }

    @Override
    public Rotation3d getRotationalVelocity() {
        final double[] xyzDps = new double[3];
        this.gyro.getRawGyro(xyzDps);
        return new Rotation3d(xyzDps[1], -xyzDps[0], xyzDps[2]);
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Gyro");
        builder.addDoubleProperty("Value", this::getAngle, null);
        builder.addDoubleProperty("Rate", this::getRate, null);
    }
}
