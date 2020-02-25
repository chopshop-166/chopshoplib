package com.chopshop166.chopshoplib.outputs;

import com.chopshop166.chopshoplib.sensors.SparkMaxEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * This wraps CANSparkMax and makes it sendable.
 * 
 * The same properties as other SpeedControllers are used for the Dashboard
 * representation. This allows reading and setting the motor speed from the
 * dashboard.
 */
public class SparkMaxSendable implements SendableSpeedController {
    private final CANSparkMax sparkMax;
    private final SparkMaxEncoder encoder;

    public SparkMaxSendable(final CANSparkMax max) {
        this.sparkMax = max;
        this.encoder = new SparkMaxEncoder(max.getEncoder());
    }

    public CANSparkMax getMotorController() {
        return sparkMax;
    }

    @Override
    public SparkMaxEncoder getEncoder() {
        return encoder;
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