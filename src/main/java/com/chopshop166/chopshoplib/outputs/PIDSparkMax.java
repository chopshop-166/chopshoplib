package com.chopshop166.chopshoplib.outputs;

import com.chopshop166.chopshoplib.sensors.SparkMaxEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * PIDSpeedController
 * <p>
 * Derives PIDSpeedController to allow for use on a SparkMax Speed Controller It
 * will act as a normal SparkMax with encoders, but will also be able to use
 * PID.
 * 
 * @author Andrew Martin
 * @since 2020-02-7
 */
public class PIDSparkMax implements PIDSpeedController {
    private final CANPIDController sparkPID;
    private final CANSparkMax max;
    private final SparkMaxEncoder encoder;

    public PIDSparkMax(final CANSparkMax max) {
        this.sparkPID = max.getPIDController();
        this.max = max;
        this.encoder = new SparkMaxEncoder(max.getEncoder());
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
    public void setSetpoint(final double setPoint) {
        sparkPID.setReference(setPoint, ControlType.kVelocity);
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
        max.set(speed);
    }

    @Override
    public double get() {
        return max.get();
    }

    @Override
    public void setInverted(final boolean isInverted) {
        max.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return max.getInverted();
    }

    @Override
    public void disable() {
        max.disable();
    }

    @Override
    public void stopMotor() {
        max.stopMotor();
    }

    @Override
    public void pidWrite(final double output) {
        max.set(output);
    }

}