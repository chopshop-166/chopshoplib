package com.chopshop166.chopshoplib.maps;

import com.chopshop166.chopshoplib.drive.MockSwerveModule;
import com.chopshop166.chopshoplib.drive.SwerveModule;
import com.chopshop166.chopshoplib.logging.LoggableMap;
import com.chopshop166.chopshoplib.logging.data.SwerveDriveData;
import com.chopshop166.chopshoplib.sensors.gyro.MockGyro;
import com.chopshop166.chopshoplib.sensors.gyro.SmartGyro;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.math.geometry.Translation2d;

/**
 * A hardware map suitable for a swerve drive.
 * 
 * All Distances are in Meters
 */
public record SwerveDriveMap(SwerveModule frontLeft, SwerveModule frontRight, SwerveModule rearLeft,
        SwerveModule rearRight, double maxDriveSpeedMetersPerSecond,
        double maxRotationRadianPerSecond, SmartGyro gyro, HolonomicPathFollowerConfig pathFollower)
        implements LoggableMap<SwerveDriveData> {

    /** A distance to use for default values. */
    private static final double DEFAULT_DISTANCE_FROM_CENTER = 0.381;

    /** Construct a map that uses mocks for everything. */
    public SwerveDriveMap() {
        this(
                // Front Left
                new MockSwerveModule(new Translation2d(DEFAULT_DISTANCE_FROM_CENTER,
                        DEFAULT_DISTANCE_FROM_CENTER)),
                // Front Right
                new MockSwerveModule(new Translation2d(DEFAULT_DISTANCE_FROM_CENTER,
                        -DEFAULT_DISTANCE_FROM_CENTER)),
                // Rear Left
                new MockSwerveModule(new Translation2d(-DEFAULT_DISTANCE_FROM_CENTER,
                        DEFAULT_DISTANCE_FROM_CENTER)),
                // Rear Right
                new MockSwerveModule(new Translation2d(-DEFAULT_DISTANCE_FROM_CENTER,
                        -DEFAULT_DISTANCE_FROM_CENTER)),
                // Max speed (m/s)
                2.0,
                // Max rotation (rad/s)
                Math.PI,
                // Gyro
                new MockGyro(),
                // Path follower config
                new HolonomicPathFollowerConfig(2.0, DEFAULT_DISTANCE_FROM_CENTER,
                        new ReplanningConfig()));
    }

    @Override
    public void updateData(final SwerveDriveData data) {
        data.frontLeft.updateData(this.frontLeft);
        data.frontRight.updateData(this.frontRight);
        data.rearLeft.updateData(this.rearLeft);
        data.rearRight.updateData(this.rearRight);
        data.gyroYawPosition = this.gyro.getRotation2d();
    }
}
