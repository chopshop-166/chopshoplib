package com.chopshop166.chopshoplib.maps;

import com.chopshop166.chopshoplib.outputs.EncodedSpeedController;
import com.chopshop166.chopshoplib.sensors.MockGyro;

import edu.wpi.first.wpilibj.GyroBase;

/**
 * Differential Drive Map
 * <p>
 * Provides a base map for differential drive and the left and right
 * {@link EncodedSpeedController}. Also provides a {@link GyroBase}
 * 
 * @author Jeffrey Burke
 * @since 2020-01-20
 */
public interface DifferentialDriveMap {
    /**
     * Get Left
     * <p>
     * Provides the left {@link EncodedSpeedController}.
     * 
     * @return {@link EncodedSpeedController}
     */
    EncodedSpeedController getLeft();

    /**
     * Get Right
     * <p>
     * Provides the right {@link EncodedSpeedController}.
     * 
     * @return {@link EncodedSpeedController}
     */
    EncodedSpeedController getRight();

    /**
     * Geta a Gyro
     * <p>
     * Provides a mock gyro unless {@link EncodedSpeedController}.
     * 
     * @return Returns a {@link MockGyro}
     */
    default GyroBase getGyro() {
        return new MockGyro();
    };
}
