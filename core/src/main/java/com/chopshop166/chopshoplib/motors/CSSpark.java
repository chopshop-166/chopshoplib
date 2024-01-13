package com.chopshop166.chopshoplib.motors;

import com.chopshop166.chopshoplib.sensors.SparkEncoder;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkPIDController;
import com.revrobotics.SparkRelativeEncoder;

/**
 * CSSpark
 *
 * Derives SmartMotorController to allow for use on a Spark Motor Controller. It will act as a
 * normal Spark with encoders, but will also be able to use PID.
 */
public class CSSpark extends SmartMotorController {
    /** The unwrapped Spark MAX object. */
    private final CANSparkBase spark;
    /** The PID controller from the Spark MAX. */
    private final SparkPIDController sparkPID;
    /** The control type for the PID controller. */
    private ControlType savedControlType = ControlType.kVelocity;
    /** The PID Slot to send along with all setReference commands. */
    private int pidSlot;

    /**
     * Create a smart motor controller from an unwrapped Spark object.
     *
     * @param spark The Spark MAX/Flex oject.
     * @param type The motor type (brushed vs brushless).
     */
    public CSSpark(final CANSparkBase spark, final MotorType type) {
        super(new MockMotorController(),
                new SparkEncoder(type == MotorType.kBrushless ? spark.getEncoder()
                        : spark.getEncoder(SparkRelativeEncoder.Type.kQuadrature, 4096)));
        this.spark = spark;
        this.sparkPID = spark.getPIDController();
    }

    /**
     * Get the wrapped speed controller.
     *
     * @return The raw Spark MAX object.
     */
    public CANSparkBase getMotorController() {
        return this.spark;
    }

    /**
     * Get the wrapped PID controller.
     *
     * @return The CAN PID object.
     */
    public SparkPIDController getPidController() {
        return this.sparkPID;
    }

    /**
     * Set the control type.
     *
     * @param controlType The controlType to set.
     */
    @Override
    public void setControlType(final PIDControlType controlType) {
        if (controlType == PIDControlType.Position) {
            this.savedControlType = ControlType.kPosition;
        } else if (controlType == PIDControlType.Velocity) {
            this.savedControlType = ControlType.kVelocity;
        }
    }

    /**
     * Set the control type to a nonstandard one.
     *
     * @param controlType The controlType to set.
     */
    public void setControlType(final ControlType controlType) {
        this.savedControlType = controlType;
    }

    /**
     * Get the control type
     *
     * @return The controlType.
     */
    public ControlType getControlType() {
        return this.savedControlType;
    }

    @Override
    public SparkEncoder getEncoder() {
        // This cast is safe because we're the ones setting it in the first place.
        return (SparkEncoder) super.getEncoder();
    }

    @Override
    public void setSetpoint(final double setPoint) {
        this.sparkPID.setReference(setPoint, this.savedControlType, this.pidSlot);
    }

    /**
     * Change what set of PID parameters are used.
     *
     * @param slotId The id of the PID parameters to use.
     */
    @Override
    public void setPidSlot(final int slotId) {
        this.pidSlot = slotId;
    }

    @Override
    public void set(final double speed) {
        this.spark.set(speed);
    }

    @Override
    public void setVoltage(final double outputVolts) {
        this.spark.setVoltage(outputVolts);
    }

    @Override
    public double get() {
        return this.spark.get();
    }

    @Override
    public void setInverted(final boolean isInverted) {
        this.spark.setInverted(isInverted);
        this.getEncoder().setReverseDirection(isInverted);
    }

    @Override
    public boolean getInverted() {
        return this.spark.getInverted();
    }

    /**
     * Get Temperature from the motor
     * 
     * @return Returns the temperature.
     */
    @Override
    public double[] getTemperatureC() {
        return new double[] {this.spark.getMotorTemperature()};
    }

    /**
     * Gets the current sent to the motor from the controller
     *
     * @return Returns the current
     */
    @Override
    public double[] getCurrentAmps() {
        return new double[] {this.spark.getOutputCurrent()};
    }

    @Override
    public void disable() {
        this.spark.disable();
    }

    @Override
    public void stopMotor() {
        this.spark.stopMotor();
    }
}
