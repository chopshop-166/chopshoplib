package com.chopshop166.chopshoplib.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * A mock swerve module that assumes it goes to the right state instantly.
 */
public class MockSwerveModule implements SwerveModule {

    /** The location of the module. */
    private final Translation2d location;
    /** The desired state of the module. */
    private SwerveModuleState desiredState = new SwerveModuleState();

    /**
     * @param location Location of the module
     */
    public MockSwerveModule(final Translation2d location) {
        this.location = location;
    }

    /**
     * Get the modules location in relation to the center of mass of the robot.
     *
     * @return Location2d object representing the offset
     */
    @Override
    public Translation2d getLocation() {
        return location;
    }

    @Override
    public void setInverted(final boolean isInverted) {
        // Assume that the mock's inversion doesn't matter.
    }

    /**
     * Process the desired state and set the output values for the motor
     * controllers.
     *
     * @param desiredState The direction and speed.
     */
    @Override
    public void setDesiredState(final SwerveModuleState desiredState) {
        this.desiredState = desiredState;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setActuator(true);
        builder.setSmartDashboardType("Swerve Module");
        builder.addDoubleProperty("Angle Error", () -> 0.0, null);
        builder.addDoubleProperty("Speed Error", () -> 0.0, null);
        builder.addDoubleProperty("Angle", () -> getAngle().getDegrees(), null);
        builder.addDoubleProperty("Speed", () -> desiredState.speedMetersPerSecond, null);
    }

    @Override
    public double getDistance() {
        return 0;
    }

    @Override
    public void resetDistance() {
        // Do nothing
    }

    @Override
    public Rotation2d getAngle() {
        return desiredState.angle;
    }

    @Override
    public SwerveModuleState getState() {
        return desiredState;
    }
}
