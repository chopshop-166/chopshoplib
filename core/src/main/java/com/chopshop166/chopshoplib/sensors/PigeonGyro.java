package com.chopshop166.chopshoplib.sensors;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.GyroBase;

/**
 * Gyro Base wrapper for the Pigeon IMU
 */
public class PigeonGyro extends GyroBase {

    /** The wrapped object. */
    final private PigeonIMU gyro;

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

    @Override
    public void close() throws Exception {
        // NoOp
    }

    @Override
    public void reset() {
        gyro.setFusedHeading(0);

    }

    @Override
    public double getRate() {
        final double[] xyz = new double[3];
        gyro.getRawGyro(xyz);
        return xyz[2];
    }

    @Override
    public double getAngle() {
        return gyro.getFusedHeading();
    }

    @Override
    public void calibrate() {
        // NoOp
    }

}