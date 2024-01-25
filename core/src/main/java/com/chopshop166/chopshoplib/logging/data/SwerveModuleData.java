package com.chopshop166.chopshoplib.logging.data;

import com.chopshop166.chopshoplib.drive.SwerveModule;
import com.chopshop166.chopshoplib.logging.DataWrapper;
import com.chopshop166.chopshoplib.logging.LogName;
import com.chopshop166.chopshoplib.logging.LoggerDataFor;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

/** Data for a single swerve module. */
@LoggerDataFor(SwerveModule.class)
public class SwerveModuleData extends DataWrapper {

    /** Actual State. */
    public final SwerveModuleStateData currentState = new SwerveModuleStateData("Current");
    /** Desired State. */
    public final SwerveModuleStateData desiredState = new SwerveModuleStateData("Desired");
    /** Drive Motor params. */
    public final MotorControllerData driveMotor = new MotorControllerData("Drive");
    /** Steering Motor params. */
    public final MotorControllerData steeringMotor = new MotorControllerData("Steering");
    /** Drive Position. */
    @LogName("DrivePositionMeters")
    public double drivePositionMeters;
    /** Actual State. */
    @LogName("DesiredVelocityMetersPerSec")
    public double desiredVelocityMetersPerSec;
    /** Actual State. */
    @LogName("DesiredAngleDegrees")
    public double desiredPodAngle;

    /**
     * Constructor.
     * 
     * @param name The name of the swerve module.
     */
    public SwerveModuleData(final String name) {
        super(name);
    }

    /**
     * Update the data with hardware data.
     * 
     * @param module The module to update from.
     */
    public void updateData(final SwerveModule module) {
        this.drivePositionMeters = module.getDistance();
        this.currentState.data = module.getState();
        this.driveMotor.updateData(module.getDriveMotor());
        this.steeringMotor.updateData(module.getSteeringMotor());

        module.setDesiredState(this.desiredState.data);
    }

    /**
     * Get the current module state.
     * 
     * @return The state.
     */
    public SwerveModuleState getModuleState() {
        return new SwerveModuleState(this.currentState.data.speedMetersPerSecond,
                this.currentState.data.angle);
    }

    /**
     * Get the module position.
     *
     * This is the integrated position.
     * 
     * @return The position.
     */
    public SwerveModulePosition getModulePosition() {
        return new SwerveModulePosition(this.drivePositionMeters, this.currentState.data.angle);
    }

    /**
     * Set Desired State.
     * 
     * @param state The desired state.
     */
    public void setDesiredState(final SwerveModuleState state) {
        this.desiredState.data = state;
    }
}
