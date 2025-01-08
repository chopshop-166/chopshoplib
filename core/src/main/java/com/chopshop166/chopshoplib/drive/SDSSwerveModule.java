package com.chopshop166.chopshoplib.drive;

import com.chopshop166.chopshoplib.motors.CSSpark;
import com.chopshop166.chopshoplib.motors.PIDControlType;
import com.chopshop166.chopshoplib.sensors.CtreEncoder;
import com.chopshop166.chopshoplib.states.PIDValues;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;

/** Swerve Drive Specialties Mk3. */
public class SDSSwerveModule implements SwerveModule {

    /** State of the module inversion */
    private boolean inverted;
    /** The physical location of the module. */
    private final Translation2d location;
    /** The encoder used for steering. */
    private final CtreEncoder steeringEncoder;
    /** The motor controller for steering. */
    private final CSSpark steeringController;
    /** The PID controller for steering. */
    private final PIDController steeringPID;
    /** The motor controller used for driving. */
    private final CSSpark driveController;

    /** Mark 3 Standard configuration. */
    public static final Configuration MK3_STANDARD = new Configuration(
            (14.0 / 50.0) * (28.0 / 16.0) * (15.0 / 60.0), Units.inchesToMeters(4));

    /** Mark 3 Fast configuration. */
    public static final Configuration MK3_FAST = new Configuration(
            (16.0 / 48.0) * (28.0 / 16.0) * (15.0 / 60.0), Units.inchesToMeters(4));

    /** Mark 4 V1 configuration. */
    public static final Configuration MK4_V1 = new Configuration(
            (14.0 / 50.0) * (25.0 / 19.0) * (15.0 / 45.0), Units.inchesToMeters(3.95));

    /** Mark 4 V2 configuration. */
    public static final Configuration MK4_V2 = new Configuration(
            (14.0 / 50.0) * (27.0 / 17.0) * (15.0 / 45.0), Units.inchesToMeters(3.95));

    /** Mark 4 V3 configuration. */
    public static final Configuration MK4_V3 = new Configuration(
            (14.0 / 50.0) * (28.0 / 16.0) * (15.0 / 45.0), Units.inchesToMeters(3.95));

    /** Mark 4 V4 configuration. */
    public static final Configuration MK4_V4 = new Configuration(
            (16.0 / 48.0) * (28.0 / 16.0) * (15.0 / 45.0), Units.inchesToMeters(3.95));

    /**
     * Module configuration.
     */
    public static class Configuration {

        /** Overall gear ratio for the swerve module drive motor. */
        public final double gearRatio;
        /** Wheel diameter. */
        public final double wheelDiameter;
        /** PID configuration. */
        public final PIDValues steeringPIDValues;

        /** PID Configuration for Drive Motor */
        public final PIDValues drivePIDValues;

        /**
         * Construct configuration data.
         *
         * @param gearRatio The gear ratio for the module.
         * @param wheelDiameter The diameter of the wheel.
         * @param pidValues The PID constants to use for the steering PID.
         * @param drivePIDValues The PID constants to use for the drive PID.
         */
        public Configuration(final double gearRatio, final double wheelDiameter,
                final PIDValues pidValues, final PIDValues drivePIDValues) {
            this.gearRatio = gearRatio;
            this.wheelDiameter = wheelDiameter;
            this.steeringPIDValues = pidValues;
            this.drivePIDValues = drivePIDValues;
        }

        /**
         * Construct configuration data.
         *
         * @param gearRatio The gear ratio for the module.
         * @param wheelDiameter The diameter of the wheel.
         * @param pidValues The PID constants to use for the steering PID.
         */
        public Configuration(final double gearRatio, final double wheelDiameter,
                final PIDValues pidValues) {
            this(gearRatio, wheelDiameter, pidValues, new PIDValues(0, 0.00015, 0, 0.219));
        }

        /**
         * Construct configuration data.
         *
         * @param gearRatio The gear ratio for the module.
         * @param wheelDiameter The diameter of the wheel.
         */
        public Configuration(final double gearRatio, final double wheelDiameter) {
            this(gearRatio, wheelDiameter, new PIDValues(0.0043, 0.00, 0.0001),
                    new PIDValues(0, 0.00015, 0, 0.219));
        }

        /**
         * Get the conversion rate.
         *
         * @return The conversion rate.
         */
        public double getConversion() {
            return this.gearRatio * Math.PI * this.wheelDiameter;
        }
    }

    /**
     * The constructor.
     *
     * @param moduleLocation The physical location in meters.
     * @param steeringEncoder The steering encoder.
     * @param steeringController The steering motor controller.
     * @param driveController The drive motor controller.
     * @param conf The module configuration.
     */
    public SDSSwerveModule(final Translation2d moduleLocation, final CtreEncoder steeringEncoder,
            final CSSpark steeringController, final CSSpark driveController,
            final Configuration conf) {
        this(moduleLocation, steeringEncoder, steeringController, driveController, conf,
                new PIDController(conf.steeringPIDValues.p(), conf.steeringPIDValues.i(),
                        conf.steeringPIDValues.d()));
    }

    /**
     * The constructor.
     *
     * @param moduleLocation The physical location.
     * @param steeringEncoder The steering encoder.
     * @param steeringController The steering motor controller.
     * @param driveController The drive motor controller.
     * @param conf The module configuration.
     * @param pid The PID controller for steering.
     */
    public SDSSwerveModule(final Translation2d moduleLocation, final CtreEncoder steeringEncoder,
            final CSSpark steeringController, final CSSpark driveController,
            final Configuration conf, final PIDController pid) {
        this.location = moduleLocation;
        this.steeringEncoder = steeringEncoder;
        this.steeringController = steeringController;
        this.driveController = configureDriveMotor(driveController, conf);
        this.steeringPID = pid;
        this.steeringPID.enableContinuousInput(-180, 180);
    }

    /**
     * Get the steering motor controller.
     *
     * @return The controller object.
     */
    @Override
    public CSSpark getSteeringMotor() {
        return this.steeringController;
    }

    /**
     * Get the drive motor controller.
     *
     * @return The controller object.
     */
    @Override
    public CSSpark getDriveMotor() {
        return this.driveController;
    }

    /**
     * Get the module's location in relation to the center of mass of the robot.
     *
     * @return Location2d object representing the offset.
     */
    @Override
    public Translation2d getLocation() {
        return this.location;
    }

    /**
     * Process the desired state and set the output values for the motor controllers.
     *
     * @param desiredState The direction and speed.
     */
    @Override
    public SwerveModuleSpeeds calculateDesiredState(final SwerveModuleState desiredState) {
        desiredState.optimize(this.getAngle());

        // Run Steering angle PID to calculate output since the Spark Max can't take
        // advantage of the CANCoder
        double angleOutput = this.steeringPID.calculate(this.getAngle().getDegrees(),
                desiredState.angle.getDegrees());
        // If we're not trying to actually drive, don't reset the module angle
        if (desiredState.speedMetersPerSecond == 0) {
            angleOutput = 0.0;
        }

        desiredState.cosineScale(this.getAngle());

        // Set the drive motor output speed
        if (desiredState.speedMetersPerSecond == 0) {
            this.driveController.getPidController().setIAccum(0);
        }
        if (this.inverted) {
            desiredState.speedMetersPerSecond *= -1;
        }

        return new SwerveModuleSpeeds(desiredState.speedMetersPerSecond, angleOutput);
    }

    @Override
    public void setInverted(final boolean isInverted) {
        this.inverted = isInverted;
    }

    /**
     * Returns the current angle of the swerve module.
     *
     * @return The current angle of the module.
     */
    @Override
    public Rotation2d getAngle() {
        return Rotation2d.fromDegrees(this.steeringEncoder.getAbsolutePosition());
    }

    /**
     * Configures a CSSparkMax for use as the drive motor on a MK3 swerve module.
     *
     * @param motor Drive motor controller to configure.
     * @return Drive motor controller for chaining.
     */
    private static CSSpark configureDriveMotor(final CSSpark motor, final Configuration conf) {
        // Get raw objects from the CSSparkMax
        final SparkBase sparkMax = motor.getMotorController();
        final var config = new SparkMaxConfig();

        // Set Motor controller configuration
        motor.setControlType(PIDControlType.Velocity);
        config.idleMode(IdleMode.kBrake);
        config.smartCurrentLimit(50);

        // Set velocity conversion to convert RPM to M/s
        // Set Position conversion to convert from Rotations to M
        config.encoder.velocityConversionFactor(conf.getConversion() / 60.0)
                .positionConversionFactor(conf.getConversion());

        // Configure PID
        // https://docs.revrobotics.com/sparkmax/operating-modes/closed-loop-control
        config.closedLoop.pidf(conf.drivePIDValues.p(), conf.drivePIDValues.i(),
                conf.drivePIDValues.d(), conf.drivePIDValues.ff());

        sparkMax.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // Return the original object so this can be chained
        return motor;
    }

    @Override
    public double getDistance() {
        return this.driveController.getEncoder().getDistance();
    }

    @Override
    public void resetDistance() {
        this.driveController.getEncoder().reset();
    }

    @Override
    public SwerveModuleState getState() {
        return new SwerveModuleState(
                (this.inverted ? -1.0 : 1.0) * this.driveController.getEncoder().getRate(),
                this.getAngle());
    }
}
