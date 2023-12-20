package com.chopshop166.chopshoplib.sensors.gyro;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;

/**
 * Gyro Base wrapper for the Pigeon IMU
 */
public class PigeonGyro implements SmartGyro {

    /** The wrapped object. */
    final private PigeonIMU gyro;
    /** Boolean to control inverted output. */
    private boolean inverted;

    /**
     * Create the wrapper.
     *
     * @param gyro The object to wrap.
     */
    public PigeonGyro(final PigeonIMU gyro) {
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

    /**
     * Sets the gyro's heading back to zero
     */
    @Override
    public void reset() {
        this.gyro.setFusedHeading(0);
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
        return this.inverted ? -this.gyro.getFusedHeading() : this.gyro.getFusedHeading();
    }

    @Override
    public void setAngle(final double angleDeg) {
        this.gyro.setFusedHeading(angleDeg);
    }

    @Override
    public void calibrate() {
        // NoOp
    }

    @Override
    public Rotation3d getRotation3d() {
        final double[] yprDeg = new double[3];
        this.gyro.getYawPitchRoll(yprDeg);
        return new Rotation3d(Units.degreesToRadians(yprDeg[2]), Units.degreesToRadians(yprDeg[1]),
                Units.degreesToRadians(yprDeg[0]));
    }

    @Override
    public Rotation3d getRotationalVelocity() {
        final double[] xyzDps = new double[3];
        this.gyro.getRawGyro(xyzDps);
        return new Rotation3d(Units.degreesToRadians(xyzDps[0]), Units.degreesToRadians(xyzDps[1]),
                Units.degreesToRadians(xyzDps[2]));
    }
}
