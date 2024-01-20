package com.chopshop166.chopshoplib.logging.data;

import com.chopshop166.chopshoplib.logging.DataWrapper;
import com.chopshop166.chopshoplib.logging.LogName;
import edu.wpi.first.math.geometry.Rotation2d;

/** Data for the entire swerve drive subsystem. */
public class SwerveDriveData extends DataWrapper {

    /** Front left swerve module. */
    public final SwerveModuleData frontLeft = new SwerveModuleData("FrontLeft");
    /** Front right swerve module. */
    public final SwerveModuleData frontRight = new SwerveModuleData("FrontRight");
    /** Rear left swerve module. */
    public final SwerveModuleData rearLeft = new SwerveModuleData("RearLeft");
    /** Rear right swerve module. */
    public final SwerveModuleData rearRight = new SwerveModuleData("RearRight");

    /** The heading of the robot. */
    @LogName("GyroYawPosition")
    public Rotation2d gyroYawPosition = new Rotation2d();

    /**
     * Constructor.
     * 
     * Uses default name.
     */
    public SwerveDriveData() {
        this("SwerveDrive");
    }

    /**
     * Constructor.
     * 
     * @param name Uses the given name for logging.
     */
    public SwerveDriveData(final String name) {
        super(name);
    }

}
