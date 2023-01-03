package com.chopshop166.chopshoplib.drive;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.DoubleSupplier;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;
import com.chopshop166.chopshoplib.maps.DifferentialDriveMap;
import com.chopshop166.chopshoplib.motors.SmartMotorController;
import com.chopshop166.chopshoplib.sensors.gyro.SmartGyro;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

/**
 * Generic Differential Drive subsystem.
 */
public class DiffDriveSubsystem extends SmartSubsystemBase {

    /** The left motor controller. */
    private final SmartMotorController left;
    /** The right motor controller. */
    private final SmartMotorController right;
    /** The gyro object. */
    private final SmartGyro gyro;
    /** The drive train object. */
    private final DifferentialDrive driveTrain;
    /** The kinematics for path following. */
    private final DifferentialDriveKinematics kinematics;
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
        super();
        this.left = map.getLeft();
        this.right = map.getRight();
        this.gyro = map.getGyro();
        this.kinematics = map.getKinematics();
        this.driveTrain = new DifferentialDrive(left, right);
        this.odometry = new DifferentialDriveOdometry(getRotation(), 0.0, 0.0);
    }

    @Override
    public void reset() {
        resetEncoders();
        resetGyro();
        driveTrain.stopMotor();
    }

    @Override
    public void safeState() {
        driveTrain.stopMotor();
    }

    @Override
    public void periodic() {
        super.periodic();
        odometry.update(getRotation(), left.getEncoder().getDistance(), right.getEncoder().getDistance());
        field.setRobotPose(getPose());
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
        return Rotation2d.fromDegrees(gyro.getAngle());
    }

    /**
     * Get the odometry pose.
     * 
     * @return The pose (translation and rotation).
     */
    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    /**
     * Reset the current position to the given pose.
     * 
     * Use to keep track of current location on the field.
     * 
     * @param pose The robot's position and rotation.
     */
    public void resetOdometry(final Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(getRotation(), 0.0, 0.0, pose);
    }

    /** Reset the encoders. */
    public void resetEncoders() {
        left.getEncoder().reset();
        right.getEncoder().reset();
    }

    /** Reset the gyro. */
    public void resetGyro() {
        gyro.reset();
    }

    /**
     * Get the turn rate.
     * 
     * @return The turn rate in degrees/second.
     */
    public double getTurnRate() {
        return gyro.getRate();
    }

    /**
     * Get an object containing wheel speeds.
     * 
     * @return Left and right wheel speeds.
     */
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(left.getEncoder().getRate(), right.getEncoder().getRate());
    }

    /**
     * Get the average encoder distance.
     *
     * @return The average distance.
     */
    private double encoderAvg() {
        return (left.getEncoder().getDistance() + right.getEncoder().getDistance()) / 2;
    }

    /**
     * Tank drive using motor controller speeds (RPM).
     * 
     * @param left  The left speed.
     * @param right The right speed.
     */
    public void tankDriveSetpoint(final double left, final double right) {
        this.left.setSetpoint(left);
        this.right.setSetpoint(right);
        driveTrain.feed();
    }

    /**
     * Drive using controller axes.
     *
     * @param forward The forward direction.
     * @param turn    The direction to turn.
     * @return A command that will run until interrupted.
     */
    public CommandBase drive(final DoubleSupplier forward, final DoubleSupplier turn) {
        return run(() -> {
            final double yAxis = forward.getAsDouble();
            final double xAxis = turn.getAsDouble();
            driveTrain.arcadeDrive(yAxis, xAxis);
        }).withName("Drive");
    }

    /**
     * Drive a given distance at the given speed.
     * 
     * @param distance The distance in meters.
     * @param speed    The speed in motor controller units.
     * @return The command.
     */
    public CommandBase driveDistance(final double distance, final double speed) {
        return cmd("Drive " + distance + " at " + speed).onInitialize(this::resetEncoders).onExecute(() -> {
            driveTrain.arcadeDrive(speed, 0);
        }).onEnd(interrupted -> {
            driveTrain.stopMotor();
        }).runsUntil(() -> encoderAvg() >= distance);
    }

    /**
     * Turn to a given relative angle at the given speed.
     * 
     * This command resets the gyro when started.
     * 
     * @param degrees The angle to turn by in degrees.
     * @param speed   The speed to turn at in motor controller units.
     * @return The command.
     */
    public CommandBase turnDegrees(final double degrees, final double speed) {
        return cmd("Turn Degrees").onInitialize(this::resetGyro).onExecute(() -> {
            double realSpeed = speed;
            if (Math.signum(degrees) != Math.signum(speed)) {
                realSpeed *= -1;
            }
            driveTrain.arcadeDrive(0, realSpeed);
        }).onEnd(interrupted -> {
            driveTrain.stopMotor();
        }).runsUntil(() -> Math.abs(gyro.getAngle()) >= Math.abs(degrees));
    }

    /**
     * Run an autonomous trajectory, resetting pose first.
     * 
     * @param trajectoryName The trajectory to run.
     * @return A command.
     */
    public CommandBase autonomousCommand(final String trajectoryName) {
        return autonomousCommand(trajectoryName, true);
    }

    /**
     * Run an autonomous trajectory.
     * 
     * @param trajectoryName The trajectory to run.
     * @param resetPose      Whether to reset the pose first.
     * @return A command.
     */
    public CommandBase autonomousCommand(final String trajectoryName, final Boolean resetPose) {

        final String trajectoryJSON = "paths/" + trajectoryName + ".wpilib.json";
        Trajectory autoTrajectory = new Trajectory();
        try {
            final Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            autoTrajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }

        final Trajectory finalAutoTrajectory = autoTrajectory;

        final RamseteCommand ramseteCommand = new RamseteCommand(autoTrajectory,
                // Gets pose
                this::getPose,
                // Creates our ramsete controller
                getRamsete(),
                // Describes how the drivetrain is influenced by motor speed
                kinematics,
                // Sends speeds to motors
                this::tankDriveSetpoint, this);

        CommandBase cmd;
        if (resetPose) {
            cmd = new InstantCommand(() -> resetOdometry(finalAutoTrajectory.getInitialPose())).andThen(ramseteCommand)
                    .andThen(driveTrain::stopMotor);
        } else {
            cmd = ramseteCommand.andThen(driveTrain::stopMotor);
        }

        cmd.setName(trajectoryName);
        return cmd;
    }
}
