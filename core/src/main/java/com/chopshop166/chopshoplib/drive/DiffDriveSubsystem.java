package com.chopshop166.chopshoplib.drive;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.DoubleSupplier;
import com.chopshop166.chopshoplib.logging.LoggedSubsystem;
import com.chopshop166.chopshoplib.logging.data.DifferentialDriveData;
import com.chopshop166.chopshoplib.maps.DifferentialDriveMap;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

/**
 * Generic Differential Drive subsystem.
 */
public class DiffDriveSubsystem
        extends LoggedSubsystem<DifferentialDriveData, DifferentialDriveMap> {

    /** The drive train object. */
    private final DifferentialDrive driveTrain;
    /** The odometry for path following. */
    private final DifferentialDriveOdometry odometry;
    /** The field object. */
    private final Field2d field = new Field2d();

    /**
     * Constructor.
     * 
     * @param map The object mapping hardware to software objects.
     */
    public DiffDriveSubsystem(final DifferentialDriveMap map) {
        super(new DifferentialDriveData(), map);
        this.driveTrain = new DifferentialDrive(map.left(), map.right());
        this.odometry = new DifferentialDriveOdometry(this.getRotation(), 0.0, 0.0);
    }

    @Override
    public void reset() {
        this.resetEncoders();
        this.resetGyro();
    }

    @Override
    public void safeState() {
        this.driveTrain.stopMotor();
    }

    @Override
    public void periodic() {
        super.periodic();
        this.odometry.update(this.getRotation(), this.getMap().leftEncoder().getDistance(),
                this.getMap().rightEncoder().getDistance());
        this.field.setRobotPose(this.getPose());
    }

    /**
     * Get the Ramsete controller that matches this chassis.
     * 
     * Meant to be overridden if necessary.
     * 
     * @return A Ramsete controller.
     */
    public RamseteController getRamsete() {
        return new RamseteController();
    }

    /**
     * Get the rotation angle.
     * 
     * @return The rotation as a WPIlib object.
     */
    public final Rotation2d getRotation() {
        return Rotation2d.fromDegrees(this.getMap().gyro().getAngle());
    }

    /**
     * Get the odometry pose.
     * 
     * @return The pose (translation and rotation).
     */
    public Pose2d getPose() {
        return this.odometry.getPoseMeters();
    }

    /**
     * Reset the current position to the given pose.
     * 
     * Use to keep track of current location on the field.
     * 
     * @param pose The robot's position and rotation.
     */
    public void resetOdometry(final Pose2d pose) {
        this.resetEncoders();
        this.odometry.resetPosition(this.getRotation(), 0.0, 0.0, pose);
    }

    /** Reset the encoders. */
    public void resetEncoders() {
        this.getMap().leftEncoder().reset();
        this.getMap().rightEncoder().reset();
    }

    /** Reset the gyro. */
    public void resetGyro() {
        this.getMap().gyro().reset();
    }

    /**
     * Get the turn rate.
     * 
     * @return The turn rate in degrees/second.
     */
    public double getTurnRate() {
        return this.getMap().gyro().getRate();
    }

    /**
     * Get an object containing wheel speeds.
     * 
     * @return Left and right wheel speeds.
     */
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(this.getMap().leftEncoder().getRate(),
                this.getMap().rightEncoder().getRate());
    }

    /**
     * Get the average encoder distance.
     *
     * @return The average distance.
     */
    private double encoderAvg() {
        return (this.getMap().leftEncoder().getDistance()
                + this.getMap().rightEncoder().getDistance()) / 2;
    }

    /**
     * Tank drive using motor controller speeds (RPM).
     * 
     * @param left The left speed.
     * @param right The right speed.
     */
    public void tankDriveSetpoint(final Double left, final Double right) {
        this.getMap().left().setSetpoint(left == null ? 0.0 : left);
        this.getMap().right().setSetpoint(left == null ? 0.0 : left);
        this.driveTrain.feed();
    }

    /**
     * Drive using controller axes.
     *
     * @param forward The forward direction.
     * @param turn The direction to turn.
     * @return A command that will run until interrupted.
     */
    public Command drive(final DoubleSupplier forward, final DoubleSupplier turn) {
        return this.run(() -> {
            final double yAxis = forward.getAsDouble();
            final double xAxis = turn.getAsDouble();
            this.driveTrain.arcadeDrive(yAxis, xAxis);
        }).withName("Drive");
    }

    /**
     * Drive a given distance at the given speed.
     * 
     * @param distance The distance in meters.
     * @param speed The speed in motor controller units.
     * @return The command.
     */
    public Command driveDistance(final double distance, final double speed) {
        return this.runOnce(this::resetEncoders).andThen(this.run(() -> {
            this.driveTrain.arcadeDrive(speed, 0);
        })).until(() -> this.encoderAvg() >= distance).finallyDo(this::safeState)
                .withName("Drive " + distance + " at " + speed);
    }

    /**
     * Turn to a given relative angle at the given speed.
     * 
     * This command resets the gyro when started.
     * 
     * @param degrees The angle to turn by in degrees.
     * @param speed The speed to turn at in motor controller units.
     * @return The command.
     */
    public Command turnDegrees(final double degrees, final double speed) {
        return this.runOnce(this::resetGyro).andThen(this.run(() -> {
            double realSpeed = speed;
            if (Math.signum(degrees) != Math.signum(speed)) {
                realSpeed *= -1;
            }
            this.driveTrain.arcadeDrive(0, realSpeed);
        })).until(() -> Math.abs(this.getMap().gyro().getAngle()) >= Math.abs(degrees))
                .finallyDo(this::safeState).withName("Turn " + degrees + " degrees");
    }

    /**
     * Run an autonomous trajectory, resetting pose first.
     * 
     * @param trajectoryName The trajectory to run.
     * @return A command.
     */
    public Command autonomousCommand(final String trajectoryName) {
        return this.autonomousCommand(trajectoryName, true);
    }

    /**
     * Run an autonomous trajectory.
     * 
     * @param trajectoryName The trajectory to run.
     * @param resetPose Whether to reset the pose first.
     * @return A command.
     */
    public Command autonomousCommand(final String trajectoryName, final Boolean resetPose) {

        final String trajectoryJSON = "paths/" + trajectoryName + ".wpilib.json";
        Trajectory autoTrajectory = new Trajectory();
        try {
            final Path trajectoryPath =
                    Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            autoTrajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON,
                    ex.getStackTrace());
        }

        final Trajectory finalAutoTrajectory = autoTrajectory;

        final RamseteCommand ramseteCommand = new RamseteCommand(autoTrajectory,
                // Gets pose
                this::getPose,
                // Creates our ramsete controller
                this.getRamsete(),
                // Describes how the drivetrain is influenced by motor speed
                this.getMap().kinematics(),
                // Sends speeds to motors
                this::tankDriveSetpoint, this);

        Command cmd = Commands.none();
        if (resetPose) {
            cmd = new InstantCommand(() -> {
                this.resetOdometry(finalAutoTrajectory.getInitialPose());
            });
        }
        return cmd.andThen(ramseteCommand).andThen(this.driveTrain::stopMotor)
                .withName(trajectoryName);
    }
}
