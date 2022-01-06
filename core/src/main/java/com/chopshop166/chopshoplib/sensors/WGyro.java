package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/** Wrapper around a gyro. */
public class WGyro implements Gyro, Sendable {

    /** The object as a gyro. */
    private final Gyro gyro;
    /** The object as a sendable. */
    private final Sendable sendable;

    /**
     * Factory method.
     * 
     * @param <GyroBase> A type that is both a {@link Gyro} and {@link Sendable}
     * @param gyro       The gyro object.
     * @return The object passed in, wrapped up.
     */
    public static <GyroBase extends Gyro & Sendable> WGyro from(final GyroBase gyro) {
        return new WGyro(gyro);
    }

    /**
     * Factory method.
     * 
     * @param gyro The gyro object.
     * @return The already wrapped object passed in.
     */
    public static WGyro from(final WGyro gyro) {
        return gyro;
    }

    /**
     * Constructor.
     * 
     * @param <GyroBase> A type that is both a {@link Gyro} and {@link Sendable}
     * @param gyro       The gyro object.
     */
    public <GyroBase extends Gyro & Sendable> WGyro(final GyroBase gyro) {
        this.gyro = gyro;
        this.sendable = gyro;
    }

    @Override
    public void close() throws Exception {
        gyro.close();

    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        sendable.initSendable(builder);

    }

    @Override
    public void calibrate() {
        gyro.calibrate();

    }

    @Override
    public void reset() {
        gyro.reset();

    }

    @Override
    public double getAngle() {
        return gyro.getAngle();
    }

    @Override
    public double getRate() {
        return gyro.getRate();
    }

}
