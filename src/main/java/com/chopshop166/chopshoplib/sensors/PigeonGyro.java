package com.chopshop166.chopshoplib.sensors;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.GyroBase;

public class PigeonGyro extends GyroBase {

    final private PigeonIMU gyro;

    public PigeonGyro(final PigeonIMU gyro) {
        super();
        this.gyro = gyro;
    }

    public PigeonGyro(final TalonSRX talon) {
        this(new PigeonIMU(talon));
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