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
public class SwerveDriveMap implements LoggableMap<SwerveDriveData> {

    /** Front left swerve module. */
    public final SwerveModule frontLeft;
    /** Front right swerve module. */
    public final SwerveModule frontRight;
    /** Rear left swerve module. */
    public final SwerveModule rearLeft;
    /** Rear right swerve module. */
    public final SwerveModule rearRight;
    /** Max drive speed (m/s). */
    public final double maxDriveSpeedMetersPerSecond;
    /** Max rotation speed (rad/s). */
    public final double maxRotationRadianPerSecond;
    /** The gyro. */
    public final SmartGyro gyro;
    /** Path follower data. */
    public final HolonomicPathFollowerConfig pathFollower;

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

    /**
     * Constructor.
     * 
     * @param frontLeft Front left swerve module.
     * @param frontRight Front right swerve module.
     * @param rearLeft Rear left swerve module.
     * @param rearRight Rear right swerve module.
     * @param maxDriveSpeedMetersPerSecond Max drive speed (m/s).
     * @param maxRotationRadianPerSecond Max rotation speed (rad/s)
     * @param gyro They gyro.
     * @param pathFollower The path follower configuration.
     */
    public SwerveDriveMap(final SwerveModule frontLeft, final SwerveModule frontRight,
            final SwerveModule rearLeft, final SwerveModule rearRight,
            final double maxDriveSpeedMetersPerSecond, final double maxRotationRadianPerSecond,
            final SmartGyro gyro, final HolonomicPathFollowerConfig pathFollower) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.rearLeft = rearLeft;
        this.rearRight = rearRight;
        this.maxDriveSpeedMetersPerSecond = maxDriveSpeedMetersPerSecond;
        this.maxRotationRadianPerSecond = maxRotationRadianPerSecond;
        this.gyro = gyro;
        this.pathFollower = pathFollower;
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
