package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public interface PIDGyro extends Gyro, Sendable {

    static PIDGyro wrap(final PIDGyro gyro) {
        return gyro;
    }

    static <T extends Gyro & Sendable> PIDGyro wrap(final T gyro) {
        return new PIDGyro() {

            @Override
            public double getAngle() {
                return gyro.getAngle();
            }

            @Override
            public double getRate() {
                return gyro.getRate();
            }

            @Override
            public void reset() {
                gyro.reset();
            }

            @Override
            public void calibrate() {
                gyro.calibrate();
            }

            @Override
            public void close() throws Exception {
                gyro.close();
            }

            @Override
            public void initSendable(SendableBuilder builder) {
                gyro.initSendable(builder);
            }
        };
    }

}