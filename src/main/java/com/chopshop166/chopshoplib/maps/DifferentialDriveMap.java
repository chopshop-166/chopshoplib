package com.chopshop166.chopshoplib.maps;

import com.chopshop166.chopshoplib.outputs.SendableSpeedController;

import edu.wpi.first.wpilibj.GyroBase;

/**
 * Differential Drive Map
 * <p>
 * Gets the Left {@link SendableSpeedController}, gets the right
 * {@link SendableSpeedController}, and gets {@link Gyro}
 * 
 * @author Jeffrey Burke
 * @since 2020-01-20
 */
public interface DifferentialDriveMap {
    public SendableSpeedController getLeft();

    public SendableSpeedController getRight();

    public GyroBase getGyro();
}
