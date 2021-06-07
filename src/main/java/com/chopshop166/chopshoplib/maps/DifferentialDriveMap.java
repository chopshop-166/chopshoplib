package com.chopshop166.chopshoplib.maps;

import com.chopshop166.chopshoplib.outputs.SmartMotorController;
import com.chopshop166.chopshoplib.sensors.MockGyro;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

/**
 * Differential Drive Map
 * 
 * Provides a base map for differential drive, using the left and right speed
 * controllers. Also provides a gyro and kinematics.
 */
public class DifferentialDriveMap {

    /** Left speed controller (or group) */
    private final SmartMotorController left;
    /** Right speed controller (or group) */
    private final SmartMotorController right;
    /** Kinematics information */
    private final DifferentialDriveKinematics kinematics;
    /** The gyro */
    private final GyroBase gyro;

    /**
     * Constructor.
     * 
     * @param left             Left speed controller.
     * @param right            Right speed controller.
     * @param trackWidthMeters Width of robot.
     */
    public DifferentialDriveMap(final SmartMotorController left, final SmartMotorController right,
            final double trackWidthMeters) {
        this(left, right, trackWidthMeters, new MockGyro());
    }

    /**
     * Constructor.
     * 
     * @param left             Left speed controller.
     * @param right            Right speed controller.
     * @param trackWidthMeters Width of robot.
     * @param gyro             The gyro.
     */
    public DifferentialDriveMap(final SmartMotorController left, final SmartMotorController right,
            final double trackWidthMeters, final GyroBase gyro) {
        this.left = left;
        this.right = right;
        this.gyro = gyro;
        this.kinematics = new DifferentialDriveKinematics(trackWidthMeters);
    }

    /**
     * Provides the left speed controller.
     * 
     * @return A {@link SmartMotorController}.
     */
    public SmartMotorController getLeft() {
        return left;
    }

    /**
     * Provides the right speed controller.
     * 
     * @return A {@link SmartMotorController}.
     */
    public SmartMotorController getRight() {
        return right;
    }

    /**
     * Gets a Gyro.
     * 
     * @return The gyro object as a {@link GyroBase}.
     */
    public GyroBase getGyro() {
        return gyro;
    }

    /**
     * Get the drive kinematics.
     * 
     * @return The kinematics object.
     */
    public DifferentialDriveKinematics getKinematics() {
        return kinematics;
    }
}
