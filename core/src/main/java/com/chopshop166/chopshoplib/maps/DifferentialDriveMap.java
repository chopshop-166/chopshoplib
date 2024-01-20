package com.chopshop166.chopshoplib.maps;

import com.chopshop166.chopshoplib.logging.LoggableMap;
import com.chopshop166.chopshoplib.logging.data.DifferentialDriveData;
import com.chopshop166.chopshoplib.motors.SmartMotorController;
import com.chopshop166.chopshoplib.sensors.IEncoder;
import com.chopshop166.chopshoplib.sensors.gyro.MockGyro;
import com.chopshop166.chopshoplib.sensors.gyro.SmartGyro;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

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
     * Default constructor.
     */
    public DifferentialDriveMap() {
        this(new SmartMotorController(), new SmartMotorController(), 1.0);
    }

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
     * Shortcut for the left encoder.
     *
     * @return The encoder object.
     */
    public IEncoder leftEncoder() {
        return this.left.getEncoder();
    }

    /**
     * Shortcut for the left encoder.
     *
     * @return The encoder object.
     */
    public IEncoder rightEncoder() {
        return this.right.getEncoder();
    }

    /**
     * Updates inputs and outputs from the subsystem mechanisms
     *
     * @param data The data object to update
     */
    @Override
    public void updateData(final DifferentialDriveData data) {
        data.left.updateData(this.left);
        data.right.updateData(this.right);
        data.gyroYawAngleDegrees = this.gyro.getAngle();
    }
}
