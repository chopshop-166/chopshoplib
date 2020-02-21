package com.chopshop166.chopshoplib.maps;

import com.chopshop166.chopshoplib.outputs.SendableSpeedController;
import com.chopshop166.chopshoplib.sensors.MockGyro;

import edu.wpi.first.wpilibj.GyroBase;

/**
 * Differential Drive Map
 * 
 * Provides a base map for differential drive, using the left and right speed
 * controllers. Also provides a gyro.
 * 
 * @author Jeffrey Burke
 * @since 2020-01-20
 */
public interface DifferentialDriveMap {
    /**
     * Provides the left speed controller.
     * 
     * @return A speed controller.
     */
    SendableSpeedController getLeft();

    /**
     * Provides the right speed controller.
     * 
     * @return A speed controller.
     */
    SendableSpeedController getRight();

    /**
     * Gets a Gyro.
     * 
     * @return Returns a {@link MockGyro}
     */
    default GyroBase getGyro() {
        return new MockGyro();
    };
}
