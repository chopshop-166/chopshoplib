package com.chopshop166.chopshoplib.maps;

import com.chopshop166.chopshoplib.logging.LoggableMap;
import com.chopshop166.chopshoplib.logging.data.DifferentialDriveData;
import com.chopshop166.chopshoplib.sensors.gyro.MockGyro;
import com.chopshop166.chopshoplib.sensors.gyro.SmartGyro;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import yams.motorcontrollers.SmartMotorController;

/**
 * Differential Drive Map
 *
 * Provides a base map for differential drive, using the left and right speed controllers. Also
 * provides a gyro and kinematics.
 */
public record DifferentialDriveMap(SmartMotorController left, SmartMotorController right,
        DifferentialDriveKinematics kinematics, SmartGyro gyro)
        implements LoggableMap<DifferentialDriveData> {

    /**
     * Constructor.
     *
     * @param left Left speed controller.
     * @param right Right speed controller.
     * @param trackWidthMeters Width of robot.
     */
    public DifferentialDriveMap(final SmartMotorController left, final SmartMotorController right,
            final double trackWidthMeters) {
        this(left, right, new DifferentialDriveKinematics(trackWidthMeters), new MockGyro());
    }

    /**
     * Updates inputs and outputs from the subsystem mechanisms
     *
     * @param data The data object to update
     */
    @Override
    public void updateData(final DifferentialDriveData data) {
        this.left.updateTelemetry();
        this.right.updateTelemetry();
        data.gyroYawAngleDegrees = this.gyro.getAngle();
    }
}
