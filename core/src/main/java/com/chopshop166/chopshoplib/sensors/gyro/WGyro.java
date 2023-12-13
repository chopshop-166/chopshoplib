package com.chopshop166.chopshoplib.sensors.gyro;

import edu.wpi.first.wpilibj.interfaces.Gyro;

/** Wrapper around a WPIlib built in gyro. */
public class WGyro implements SmartGyro {

    /** The object as a gyro. */
    private final Gyro gyro;
    /** The offset from the zero-position, for manual alignment. */
    private double offset;

    /**
     * Constructor.
     *
     * @param gyro The gyro object.
     */
    public WGyro(final Gyro gyro) {
        this.gyro = gyro;
    }

    @Override
    public void close() throws Exception {
        this.gyro.close();
    }

    @Override
    public void calibrate() {
        this.gyro.calibrate();
    }

    @Override
    public void reset() {
        this.gyro.reset();
        this.offset = 0;
    }

    @Override
    public double getAngle() {
        return this.gyro.getAngle() - this.offset;
    }

    @Override
    public void setAngle(final double angle) {
        this.offset = angle;
    }

    @Override
    public double getRate() {
        return this.gyro.getRate();
    }

}
