package com.chopshop166.chopshoplib.maps;

import com.chopshop166.chopshoplib.sensors.WGyro;
import com.chopshop166.chopshoplib.motors.SmartMotorController;
import com.chopshop166.chopshoplib.sensors.MockGyro;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.interfaces.Gyro;

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
    private final WGyro gyro;

    /**
     * Default constructor.
     */
    public DifferentialDriveMap() {
        this(new SmartMotorController(), new SmartMotorController(), 1.0, new MockGyro());
    }

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
     * @param <GyroBase>       A gyro type.
     * @param left             Left speed controller.
     * @param right            Right speed controller.
     * @param trackWidthMeters Width of robot.
     * @param gyro             The gyro.
     */
    public <GyroBase extends Gyro & Sendable> DifferentialDriveMap(final SmartMotorController left,
            final SmartMotorController right,
            final double trackWidthMeters, final GyroBase gyro) {
        this.left = left;
        this.right = right;
        this.gyro = new WGyro(gyro);
        this.kinematics = new DifferentialDriveKinematics(trackWidthMeters);
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
            final double trackWidthMeters, final WGyro gyro) {
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
     * @return The gyro object as a {@link WGyro}.
     */
    public WGyro getGyro() {
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
