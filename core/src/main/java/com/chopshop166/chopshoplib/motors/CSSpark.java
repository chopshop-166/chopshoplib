package com.chopshop166.chopshoplib.motors;

import com.chopshop166.chopshoplib.sensors.IEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;

/**
 * CSSpark
 *
 * Derives SmartMotorController to allow for use on a Spark Motor Controller. It will act as a
 * normal Spark with encoders, but will also be able to use PID.
 */
public class CSSpark extends SmartMotorController {
    /** The unwrapped Spark MAX object. */
    private final SparkBase spark;
    /** The PID controller from the Spark MAX. */
    private final SparkClosedLoopController sparkPID;
    /** The control type for the PID controller. */
    private ControlType savedControlType = ControlType.kDutyCycle;
    /** The PID Slot to send along with all setReference commands. */
    private ClosedLoopSlot pidSlot;

    /**
     * Create a smart motor controller from an unwrapped Spark object.
     *
     * @param spark The Spark MAX/Flex object.
     * @param encoder The wrapped encoder.
     */
    protected CSSpark(final SparkBase spark, final IEncoder encoder) {
        super(spark, encoder);
        this.spark = spark;
        this.sparkPID = spark.getClosedLoopController();
    }

    /**
     * Get the wrapped speed controller.
     *
     * @return The raw Spark MAX object.
     */
    public SparkBase getMotorController() {
        return this.spark;
    }

    /**
     * Get the wrapped PID controller.
     *
     * @return The CAN PID object.
     */
    public SparkClosedLoopController getPidController() {
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
    public void setSetpoint(final double setPoint) {
        this.sparkPID.setSetpoint(setPoint, this.savedControlType, this.pidSlot);
    }

    /**
     * Change what set of PID parameters are used.
     *
     * @param slotId The id of the PID parameters to use.
     */
    @Override
    public void setPidSlot(final int slotId) {
        this.pidSlot = switch (slotId) {
            case 0 -> ClosedLoopSlot.kSlot0;
            case 1 -> ClosedLoopSlot.kSlot1;
            case 2 -> ClosedLoopSlot.kSlot2;
            case 3 -> ClosedLoopSlot.kSlot3;
            default -> ClosedLoopSlot.kSlot0;
        };
    }

    @Override
    public void set(final double speed) {
        if (this.savedControlType == ControlType.kDutyCycle) {
            this.spark.set(speed);
        } else {
            this.setSetpoint(speed);
        }
    }

    @Override
    public void setVoltage(final double outputVolts) {
        this.spark.setVoltage(outputVolts);
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
    public double[] getVoltage() {
        return new double[] {this.spark.getBusVoltage() * this.spark.getAppliedOutput()};
    }

    @Override
    public int[] getFaultData() {
        return new int[] {this.spark.getFaults().rawBits};
    }

    @Override
    public int[] getStickyFaultData() {
        return new int[] {this.spark.getStickyFaults().rawBits};
    }

    @Override
    public String getMotorControllerType() {
        return "Spark";
    }
}
