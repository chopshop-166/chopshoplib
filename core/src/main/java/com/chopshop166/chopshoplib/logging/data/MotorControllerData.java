package com.chopshop166.chopshoplib.logging.data;

import com.chopshop166.chopshoplib.logging.DataWrapper;
import com.chopshop166.chopshoplib.logging.LogName;
import com.chopshop166.chopshoplib.logging.NoLog;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import yams.motorcontrollers.SmartMotorController;

/**
 * Data object for a motor controller.
 */
public class MotorControllerData extends DataWrapper {

    /** The setpoint of the motor. */
    @LogName("Setpoint")
    public double setpoint;
    /** The distance the motor has traveled in inches. */
    @LogName("Distance")
    public Distance distance;
    /** The motor's velocity in inches/second. */
    @LogName("Velocity")
    public LinearVelocity velocity;
    /** The motor current in amps. */
    @LogName("CurrentAmps")
    public Current currentAmps;
    /** The motor temperature in celcius. */
    @LogName("TempCelsius")
    public Temperature tempC;
    /** The motor output voltage. */
    @LogName("Voltage")
    public Voltage voltage;
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
        motor.setDutyCycle(this.setpoint);
        motor.updateTelemetry();
    }
}
