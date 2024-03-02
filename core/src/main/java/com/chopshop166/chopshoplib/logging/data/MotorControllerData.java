package com.chopshop166.chopshoplib.logging.data;

import com.chopshop166.chopshoplib.logging.DataWrapper;
import com.chopshop166.chopshoplib.logging.LogName;
import com.chopshop166.chopshoplib.logging.LoggerDataFor;
import com.chopshop166.chopshoplib.logging.NoLog;
import com.chopshop166.chopshoplib.motors.SmartMotorController;

/**
 * Data object for a motor controller.
 */
@LoggerDataFor(SmartMotorController.class)
public class MotorControllerData extends DataWrapper {

    /** The setpoint of the motor. */
    @LogName("Setpoint")
    public double setpoint;
    /** The distance the motor has traveled in inches. */
    @LogName("Distance")
    public double distance;
    /** The motor's velocity in inches/second. */
    @LogName("Velocity")
    public double velocity;
    /** The motor current in amps. */
    @LogName("CurrentAmps")
    public double[] currentAmps;
    /** The motor temperature in celcius. */
    @LogName("TempCelsius")
    public double[] tempC;
    /** The motor output voltage. */
    @LogName("Voltage")
    public double[] voltage;
    /** The faults that are set. */
    @LogName("Faults")
    public int[] faults;
    /** The sticky faults that are set. */
    @LogName("Sticky Faults")
    public int[] stickyFaults;
    /** The type of motor controller. */
    @LogName("Motor Type")
    public String motorType;

    /** Whether the motor is a flywheel. */
    @NoLog
    public boolean isFlywheel;

    /**
     * Constructor.
     */
    public MotorControllerData() {
        this(false);
    }

    /**
     * Constructor.
     *
     * @param isFlywheel Whether the motor is a flywheel.
     */
    public MotorControllerData(final boolean isFlywheel) {
        super();
        this.isFlywheel = isFlywheel;
    }

    /**
     * Update data from the motor.
     *
     * @param motor The motor object to update from.
     */
    public void updateData(final SmartMotorController motor) {
        if (this.isFlywheel && this.setpoint == 0.0) {
            motor.stopMotor();
        } else {
            motor.set(this.setpoint);
        }
        this.distance = motor.getEncoder().getDistance();
        this.velocity = motor.getEncoder().getRate();
        this.currentAmps = motor.getCurrentAmps();
        this.tempC = motor.getTemperatureC();
        this.voltage = motor.getVoltage();
        this.faults = motor.getFaultData();
        this.stickyFaults = motor.getStickyFaultData();
        this.motorType = motor.getMotorControllerType();
    }
}
