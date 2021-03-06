package com.chopshop166.chopshoplib.outputs;

import com.chopshop166.chopshoplib.sensors.SparkMaxEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * PIDSpeedController
 *
 * Derives PIDSpeedController to allow for use on a SparkMax Speed Controller.
 * It will act as a normal SparkMax with encoders, but will also be able to use
 * PID.
 * 
 * @author Andrew Martin
 * @since 2020-02-7
 */
public class PIDSparkMax implements PIDSpeedController {
    /** The unwrapped Spark MAX object. */
    private final CANSparkMax sparkMax;
    /** The encoder for the Spark MAX. */
    private final SparkMaxEncoder encoder;
    /** The PID controller from the Spark MAX. */
    private final CANPIDController sparkPID;
    /** The control type for the PID controller. */
    private ControlType controlType = ControlType.kVelocity;

    /**
     * Create a PID Spark MAX from an unwrapped Spark MAX object.
     * 
     * @param max The Spark MAX oject.
     */
    public PIDSparkMax(final CANSparkMax max) {
        this.sparkMax = max;
        this.encoder = new SparkMaxEncoder(max.getEncoder());
        this.sparkPID = max.getPIDController();
    }

    /**
     * Get the wrapped speed controller.
     * 
     * @return The raw Spark MAX object.
     */
    public CANSparkMax getMotorController() {
        return sparkMax;
    }

    /**
     * Get the wrapped PID controller.
     * 
     * @return The CAN PID object.
     */
    public CANPIDController getPidController() {
        return sparkPID;
    }

    /**
     * Set the control type
     * 
     * @param controlType The controlType to set.
     */
    public void setControlType(final ControlType controlType) {
        this.controlType = controlType;
    }

    @Override
    public SparkMaxEncoder getEncoder() {
        return encoder;
    }

    @Override
    public void setP(final double kp) {
        sparkPID.setP(kp);
    }

    @Override
    public void setI(final double ki) {
        sparkPID.setI(ki);
    }

    @Override
    public void setD(final double kd) {
        sparkPID.setD(kd);
    }

    @Override
    public void setF(final double kf) {
        sparkPID.setFF(kf);
    }

    @Override
    public void setSetpoint(final double setPoint) {
        sparkPID.setReference(setPoint, controlType);
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Speed Controller");
        builder.setActuator(true);
        builder.setSafeState(this::disable);
        builder.addDoubleProperty("Value", this::get, this::set);
    }

    @Override
    public void set(final double speed) {
        sparkMax.set(speed);
    }

    @Override
    public double get() {
        return sparkMax.get();
    }

    @Override
    public void setInverted(final boolean isInverted) {
        sparkMax.setInverted(isInverted);
        encoder.setReverseDirection(isInverted);
    }

    @Override
    public boolean getInverted() {
        return sparkMax.getInverted();
    }

    @Override
    public void disable() {
        sparkMax.disable();
    }

    @Override
    public void stopMotor() {
        sparkMax.stopMotor();
    }

    @Override
    public void pidWrite(final double output) {
        sparkMax.pidWrite(output);
    }
}