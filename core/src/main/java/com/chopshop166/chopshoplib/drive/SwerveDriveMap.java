package com.chopshop166.chopshoplib.drive;

import com.chopshop166.chopshoplib.sensors.gyro.MockGyro;
import com.chopshop166.chopshoplib.sensors.gyro.SmartGyro;

import edu.wpi.first.math.geometry.Translation2d;

/**
 * A hardware map suitable for a swerve drive.
 * 
 * All Distances are in Meters
 */
public record SwerveDriveMap(SwerveModule frontLeft, SwerveModule frontRight, SwerveModule rearLeft,
        SwerveModule rearRight, double maxDriveSpeedMetersPerSecond,
        double maxRotationRadianPerSecond, SmartGyro gyro) {

    /** A distance to use for default values. */
    private static final double DEFAULT_DISTANCE_FROM_CENTER = 0.381;

    /** Construct a map that uses mocks for everything. */
    public SwerveDriveMap() {
        this(
                // Front Left
                new MockSwerveModule(
                        new Translation2d(SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER,
                                SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER)),
                // Front Right
                new MockSwerveModule(
                        new Translation2d(SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER,
                                -SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER)),
                // Rear Left
                new MockSwerveModule(
                        new Translation2d(-SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER,
                                SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER)),
                // Rear Right
                new MockSwerveModule(
                        new Translation2d(-SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER,
                                -SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER)),
                // Max speed (m/s)
                2.0,
                // Max rotation (rad/s)
                Math.PI,
                // Gyro
                new MockGyro());
    }
}