package com.chopshop166.chopshoplib.logging.data;

import com.chopshop166.chopshoplib.logging.DataWrapper;
import com.chopshop166.chopshoplib.logging.LogName;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

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

    /**
     * Set Desired States.
     * 
     * @param states The desired states.
     */
    public void setDesiredStates(final SwerveModuleState... states) {
        this.frontLeft.setDesiredState(states[0]);
        this.frontRight.setDesiredState(states[1]);
        this.rearLeft.setDesiredState(states[2]);
        this.rearRight.setDesiredState(states[3]);
    }

}
