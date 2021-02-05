package com.chopshop166.chopshoplib.maps;

import com.chopshop166.chopshoplib.outputs.SmartSpeedController;
import com.chopshop166.chopshoplib.sensors.MockGyro;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

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
    SmartSpeedController getLeft();

    /**
     * Provides the right speed controller.
     * 
     * @return A speed controller.
     */
    SmartSpeedController getRight();

    /**
     * Gets a Gyro.
     * 
     * @return Returns a {@link MockGyro}
     */
    default GyroBase getGyro() {
        return new MockGyro();
    };

    /**
     * Gets Drive Kinematics
     * 
     * @return Returns a description of differential drive in terms of drivetrain
     *         dimentions
     */
    default DifferentialDriveKinematics getDriveKinematics() {
        return new DifferentialDriveKinematics(1);
    }
}
