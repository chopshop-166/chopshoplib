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
    public SwerveModuleState currentState = new SwerveModuleState();
    /** Desired State. */
    public SwerveModuleState desiredState = new SwerveModuleState();
    /** Drive Motor params. */
    public final MotorControllerData driveMotor = new MotorControllerData();
    /** Steering Motor params. */
    public final MotorControllerData steeringMotor = new MotorControllerData();
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
     * Update the data with hardware data.
     * 
     * @param module The module to update from.
     */
    public void updateData(final SwerveModule module) {
        this.drivePositionMeters = module.getDistance();
        this.currentState = module.getState();
        this.driveMotor.updateData(module.getDriveMotor());
        this.steeringMotor.updateData(module.getSteeringMotor());

        module.setDesiredState(this.desiredState);
    }

    /**
     * Get the current module state.
     * 
     * @return The state.
     */
    public SwerveModuleState getModuleState() {
        return this.currentState;
    }

    /**
     * Get the module position.
     *
     * This is the integrated position.
     * 
     * @return The position.
     */
    public SwerveModulePosition getModulePosition() {
        return new SwerveModulePosition(this.drivePositionMeters, this.currentState.angle);
    }

    /**
     * Set Desired State.
     * 
     * @param state The desired state.
     */
    public void setDesiredState(final SwerveModuleState state) {
        this.desiredState = state;
    }
}
