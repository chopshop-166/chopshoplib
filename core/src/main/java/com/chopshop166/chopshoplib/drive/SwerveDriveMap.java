package com.chopshop166.chopshoplib.drive;

import com.chopshop166.chopshoplib.sensors.gyro.MockGyro;
import com.chopshop166.chopshoplib.sensors.gyro.SmartGyro;

import edu.wpi.first.math.geometry.Translation2d;

/**
 * A hardware map suitable for a swerve drive.
 * 
 * All Distances are in Meters
 */
public class SwerveDriveMap {

    /** The front left module. */
    private final SwerveModule frontLeft;
    /** The front right module. */
    private final SwerveModule frontRight;
    /** The rear left module. */
    private final SwerveModule rearLeft;
    /** The rear right module. */
    private final SwerveModule rearRight;
    /** The maximum drive speed. */
    private final double maxDriveSpeedMetersPerSecond;
    /** The maximum rotation speed. */
    private final double maxRotationRadianPerSecond;
    /** The gyro for heading. */
    private final SmartGyro gyro;

    /** A distance to use for default values. */
    private static final double DEFAULT_DISTANCE_FROM_CENTER = 0.381;

    /** Construct a map that uses mocks for everything. */
    public SwerveDriveMap() {

        this.frontLeft = new MockSwerveModule(
                new Translation2d(SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER,
                        SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER));

        this.frontRight = new MockSwerveModule(
                new Translation2d(SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER,
                        -SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER));

        this.rearLeft = new MockSwerveModule(
                new Translation2d(-SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER,
                        SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER));

        this.rearRight = new MockSwerveModule(
                new Translation2d(-SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER,
                        -SwerveDriveMap.DEFAULT_DISTANCE_FROM_CENTER));

        this.maxDriveSpeedMetersPerSecond = 2;

        this.maxRotationRadianPerSecond = Math.PI;

        this.gyro = new MockGyro();

    }

    /**
     * Construct the map.
     * 
     * @param frontLeft                    The front left swerve module.
     * @param frontRight                   The front right swerve module.
     * @param rearLeft                     The rear left swerve module.
     * @param rearRight                    The rear right swerve module.
     * @param maxDriveSpeedMetersPerSecond The maximum drive speed.
     * @param maxRotationRadianPerSecond   The maximum rotation speed.
     * @param gyro                         The gyro object.
     */
    public SwerveDriveMap(final SwerveModule frontLeft, final SwerveModule frontRight, final SwerveModule rearLeft,
            final SwerveModule rearRight, final double maxDriveSpeedMetersPerSecond,
            final double maxRotationRadianPerSecond, final SmartGyro gyro) {

        this.frontLeft = frontLeft;

        this.frontRight = frontRight;

        this.rearLeft = rearLeft;

        this.rearRight = rearRight;

        this.maxDriveSpeedMetersPerSecond = maxDriveSpeedMetersPerSecond;

        this.maxRotationRadianPerSecond = maxRotationRadianPerSecond;

        this.gyro = gyro;
    }

    public SwerveModule getFrontLeft() {
        return this.frontLeft;
    }

    public SwerveModule getFrontRight() {
        return this.frontRight;
    }

    public SwerveModule getRearLeft() {
        return this.rearLeft;
    }

    public SwerveModule getRearRight() {
        return this.rearRight;
    }

    public double getMaxDriveSpeedMetersPerSecond() {
        return this.maxDriveSpeedMetersPerSecond;
    }

    public double getMaxRotationRadianPerSecond() {
        return this.maxRotationRadianPerSecond;
    }

    public SmartGyro getGyro() {
        return this.gyro;
    }
}