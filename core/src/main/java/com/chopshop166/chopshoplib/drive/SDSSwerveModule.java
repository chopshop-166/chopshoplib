package com.chopshop166.chopshoplib.drive;

import com.chopshop166.chopshoplib.motors.CSSparkMax;
import com.chopshop166.chopshoplib.motors.PIDControlType;
import com.chopshop166.chopshoplib.states.PIDValues;
import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.sendable.SendableBuilder;

/** Swerve Drive Specialties Mk3. */
public class SDSSwerveModule implements SwerveModule {

    /** State of the module inversion */
    private boolean inverted;
    /** The physical location of the module. */
    private final Translation2d location;
    /** The encoder used for steering. */
    private final CANCoder steeringEncoder;
    /** The motor controller for steering. */
    private final CSSparkMax steeringController;
    /** The PID controller for steering. */
    private final PIDController steeringPID;
    /** The motor controller used for driving. */
    private final CSSparkMax driveController;

    /** The last calculated speed error. */
    private double speedError;

    /** Mark 3 Standard configuration. */
    public static final Configuration MK3_STANDARD = new Configuration((14.0 / 50.0) * (28.0 / 16.0) * (15.0 / 60.0),
            Units.inchesToMeters(4));

    /** Mark 3 Fast configuration. */
    public static final Configuration MK3_FAST = new Configuration((16.0 / 48.0) * (28.0 / 16.0) * (15.0 / 60.0),
            Units.inchesToMeters(4));

    /** Mark 4 V1 configuration. */
    public static final Configuration MK4_V1 = new Configuration((14.0 / 50.0) * (25.0 / 19.0) * (15.0 / 45.0),
            Units.inchesToMeters(3.95));

    /** Mark 4 V2 configuration. */
    public static final Configuration MK4_V2 = new Configuration((14.0 / 50.0) * (27.0 / 17.0) * (15.0 / 45.0),
            Units.inchesToMeters(3.95));

    /** Mark 4 V3 configuration. */
    public static final Configuration MK4_V3 = new Configuration((14.0 / 50.0) * (28.0 / 16.0) * (15.0 / 45.0),
            Units.inchesToMeters(3.95));

    /** Mark 4 V4 configuration. */
    public static final Configuration MK4_V4 = new Configuration((16.0 / 48.0) * (28.0 / 16.0) * (15.0 / 45.0),
            Units.inchesToMeters(3.95));

    /** PID P value. */
    private static final PIDValues PID_VALUES = new PIDValues(0.0043, 0.00, 0.0001);

    /**
     * Module configuration.
     */
    public static class Configuration {

        /** Overall gear ratio for the swerve module drive motor. */
        public final double gearRatio;
        /** Wheel diameter. */
        public final double wheelDiameter;

        /**
         * Construct configuration data.
         * 
         * @param gearRatio     The gear ratio for the module.
         * @param wheelDiameter The diameter of the wheel.
         */
        public Configuration(final double gearRatio, final double wheelDiameter) {
            this.gearRatio = gearRatio;
            this.wheelDiameter = wheelDiameter;
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
     * @param moduleLocation     The physical location in meters.
     * @param steeringEncoder    The steering encoder.
     * @param steeringController The steering motor controller.
     * @param driveController    The drive motor controller.
     * @param conf               The module configuration.
     */
    public SDSSwerveModule(final Translation2d moduleLocation, final CANCoder steeringEncoder,
            final CSSparkMax steeringController, final CSSparkMax driveController, final Configuration conf) {
        this(moduleLocation, steeringEncoder, steeringController, driveController, conf,
                new PIDController(PID_VALUES.p(), PID_VALUES.i(), PID_VALUES.d()));
    }

    /**
     * The constructor.
     *
     * @param moduleLocation     The physical location.
     * @param steeringEncoder    The steering encoder.
     * @param steeringController The steering motor controller.
     * @param driveController    The drive motor controller.
     * @param conf               The module configuration.
     * @param pid                The PID controller for steering.
     */
    public SDSSwerveModule(final Translation2d moduleLocation, final CANCoder steeringEncoder,
            final CSSparkMax steeringController, final CSSparkMax driveController, final Configuration conf,
            final PIDController pid) {
        this.location = moduleLocation;
        this.steeringEncoder = steeringEncoder;
        this.steeringController = steeringController;
        this.driveController = SDSSwerveModule.configureDriveMotor(driveController, conf);
        this.steeringPID = pid;
        this.steeringPID.enableContinuousInput(-180, 180);
    }

    /**
     * Get the steering motor controller.
     *
     * @return The controller object.
     */
    public CSSparkMax getSteeringController() {
        return this.steeringController;
    }

    /**
     * Get the Disney motor controller.
     *
     * @return The controller object.
     */
    public CSSparkMax getDriveController() {
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
     * Process the desired state and set the output values for the motor
     * controllers.
     *
     * @param desiredState The direction and speed.
     */
    @Override
    public void setDesiredState(final SwerveModuleState desiredState) {
        final SwerveModuleState state = this.calculateSteeringAngle(desiredState);

        // Run Steering angle PID to calculate output since the Spark Max can't take
        // advantage of the Cancoder
        final double angleOutput = this.steeringPID.calculate(this.getAngle().getDegrees(), state.angle.getDegrees());
        // If we're not trying to actually drive, don't reset the module angle
        if (state.speedMetersPerSecond == 0) {
            this.steeringController.set(0);
        } else {
            this.steeringController.set(angleOutput);
        }

        // Set the drive motor output speed
        if (state.speedMetersPerSecond == 0) {
            this.driveController.getPidController().setIAccum(0);
        }
        this.speedError = state.speedMetersPerSecond - this.driveController.getEncoder().getRate();
        if (this.inverted) {
            this.driveController.setSetpoint(-state.speedMetersPerSecond);
        } else {
            this.driveController.setSetpoint(state.speedMetersPerSecond);
        }
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
     * Optimizes the desired module angle by taking into account the current module
     * angle.
     *
     * @param desiredState The module state as calculated by a SwerveDriveKinematics
     *                     object.
     * @return The optimized module state.
     */
    private SwerveModuleState calculateSteeringAngle(final SwerveModuleState desiredState) {
        return SwerveModuleState.optimize(desiredState, this.getAngle());
    }

    /**
     * Configures a CSSparkMax for use as the drive motor on a MK3 swerve module.
     *
     * @param motor Drive motor controller to configure.
     * @return Drive motor controller for chaining.
     */
    private static CSSparkMax configureDriveMotor(final CSSparkMax motor, final Configuration conf) {
        // Get raw objects from the CSSparkMax
        final CANSparkMax sparkMax = motor.getMotorController();
        final RelativeEncoder encoder = motor.getEncoder().getRaw();
        final SparkMaxPIDController pid = motor.getPidController();
        sparkMax.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 40);
        sparkMax.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 500);

        // Set Motor controller configuration
        motor.setControlType(PIDControlType.Velocity);
        sparkMax.setIdleMode(CANSparkMax.IdleMode.kBrake);
        sparkMax.setSmartCurrentLimit(50);
        // Set velocity conversion to convert RPM to M/s
        encoder.setVelocityConversionFactor(conf.getConversion() / 60.0);
        // Set Position conversion to convert from Rotations to M
        encoder.setPositionConversionFactor(conf.getConversion());

        // Configure PID
        // https://docs.revrobotics.com/sparkmax/operating-modes/closed-loop-control
        pid.setP(0.0);
        pid.setI(0.00015);
        pid.setD(0.0);
        pid.setFF(0.219);

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
    public void initSendable(final SendableBuilder builder) {
        builder.setActuator(true);
        builder.setSmartDashboardType("Swerve Module");
        builder.addDoubleProperty("Angle Error", this.steeringPID::getPositionError, null);
        builder.addDoubleProperty("Speed Error", () -> this.speedError, null);
        builder.addDoubleProperty("Angle", () -> this.getAngle().getDegrees(), null);
        builder.addDoubleProperty("Speed", () -> this.driveController.getEncoder().getRate(), null);
    }

    @Override
    public SwerveModuleState getState() {
        return new SwerveModuleState((this.inverted ? 1.0 : -1.0) * this.driveController.getEncoder().getRate(),
                this.getAngle());
    }
}
