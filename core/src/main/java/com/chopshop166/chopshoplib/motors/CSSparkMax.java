package com.chopshop166.chopshoplib.motors;

import com.chopshop166.chopshoplib.sensors.SparkMaxEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * WSparkMax
 *
 * Derives SmartMotorController to allow for use on a SparkMax Speed Controller. It will act as a
 * normal SparkMax with encoders, but will also be able to use PID.
 */
public class CSSparkMax extends SmartMotorController {
    /** The unwrapped Spark MAX object. */
    private final CANSparkMax sparkMax;
    /** The PID controller from the Spark MAX. */
    private final SparkMaxPIDController sparkPID;
    /** The control type for the PID controller. */
    private ControlType savedControlType = ControlType.kVelocity;
    /** The PID Slot to send along with all setReference commands. */
    private int pidSlot;

    /**
     * Create a PID Spark MAX from an unwrapped Spark MAX object.
     *
     * @param max The Spark MAX oject.
     */
    public CSSparkMax(final CANSparkMax max) {
        super(new MockMotorController(), new SparkMaxEncoder(max.getEncoder()));
        this.sparkMax = max;
        this.sparkPID = max.getPIDController();
    }

    /**
     * Create a new wrapped SPARK MAX Controller.
     *
     * @param deviceID The device ID.
     * @param type The motor type connected to the controller. Brushless motors must be connected to
     *        their matching color and the hall sensor plugged in. Brushed motors must be connected
     *        to the Red and Black terminals only.
     */
    public CSSparkMax(final int deviceID, final MotorType type) {
        this(new CANSparkMax(deviceID, type));
    }

    /**
     * Get the wrapped speed controller.
     *
     * @return The raw Spark MAX object.
     */
    public CANSparkMax getMotorController() {
        return this.sparkMax;
    }

    /**
     * Get the wrapped PID controller.
     *
     * @return The CAN PID object.
     */
    public SparkMaxPIDController getPidController() {
        return this.sparkPID;
    }

    /**
     * Add a validator to make sure that the current is below a provided limit.
     * 
     * @param limit The maximum current to allow.
     * @param filterTimeConstant The time constant of the IIR filter.
     */
    public void validateCurrent(final double limit, final double filterTimeConstant) {
        final LinearFilter currentFilter = LinearFilter.singlePoleIIR(filterTimeConstant, 0.02);
        this.addValidator(() -> currentFilter.calculate(this.sparkMax.getOutputCurrent()) < limit);
    }

    /**
     * Set the control type.
     *
     * @param controlType The controlType to set.
     */
    @Override
    public void setControlType(final PIDControlType controlType) {
        if (controlType == PIDControlType.Position) {
            this.savedControlType = CANSparkMax.ControlType.kPosition;
        } else if (controlType == PIDControlType.Velocity) {
            this.savedControlType = CANSparkMax.ControlType.kVelocity;
        }
    }

    /**
     * Set the control type to a nonstandard one.
     *
     * @param controlType The controlType to set.
     */
    public void setControlType(final CANSparkMax.ControlType controlType) {
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
    public SparkMaxEncoder getEncoder() {
        // This cast is safe because we're the ones setting it in the first place.
        return (SparkMaxEncoder) super.getEncoder();
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
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Motor Controller");
        builder.setActuator(true);
        builder.setSafeState(this::disable);
        builder.addDoubleProperty("Value", this::get, this::set);
        builder.addDoubleArrayProperty("Temperature", this::getTemperatureC, null);
        builder.addDoubleArrayProperty("Current", this::getCurrentAmps, null);
    }

    @Override
    public void set(final double speed) {
        this.sparkMax.set(speed);
    }

    @Override
    public void setVoltage(final double outputVolts) {
        this.sparkMax.setVoltage(outputVolts);
    }

    @Override
    public double get() {
        return this.sparkMax.get();
    }

    @Override
    public void setInverted(final boolean isInverted) {
        this.sparkMax.setInverted(isInverted);
        this.getEncoder().setReverseDirection(isInverted);
    }

    @Override
    public boolean getInverted() {
        return this.sparkMax.getInverted();
    }

    /**
     * Get Temperature from the motor
     * 
     * @return Returns the temperature.
     */
    @Override
    public double[] getTemperatureC() {
        return new double[] { this.sparkMax.getMotorTemperature() };
    }

    /**
     * Gets the current sent to the motor from the controller
     *
     * @return Returns the current
     */
    @Override
    public double[] getCurrentAmps() {
        return new double[] { this.sparkMax.getOutputCurrent() };
    }

    @Override
    public void disable() {
        this.sparkMax.disable();
    }

    @Override
    public void stopMotor() {
        this.sparkMax.stopMotor();
    }
}
