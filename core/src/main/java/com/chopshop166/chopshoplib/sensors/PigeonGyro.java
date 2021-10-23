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
    private boolean inverted = false;

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
     * @throws Exception
     */
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
     * @return The rate in which we have traveled in degrees per second
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

    /**
	 * Inverts the angle and rate of the Pigeon
	 *
	 * @param isInverted The state of inversion, true is inverted.
	 */
    @Override
    public void setInverted(boolean isInverted) {
        inverted = isInverted;
    }
}