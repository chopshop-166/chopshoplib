package com.chopshop166.chopshoplib.logging.data;

import com.chopshop166.chopshoplib.logging.LogName;
import com.chopshop166.chopshoplib.logging.LoggerDataFor;
import com.chopshop166.chopshoplib.logging.DataWrapper;
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
    @LogName("DistanceInches")
    public double distanceInches;
    /** The motor's velocity in inches/second. */
    @LogName("VelocityInchesPerSec")
    public double velocityInchesPerSec;
    /** The motor current in amps. */
    @LogName("CurrentAmps")
    public double[] currentAmps;
    /** The motor temperature in celcius. */
    @LogName("TempCelsius")
    public double[] tempC;

    /**
     * Constructor.
     * 
     * @param name The name of the motor controller.
     */
    public MotorControllerData(final String name) {
        super(name);
    }

    /**
     * Update data from the motor.
     * 
     * @param motor The motor object to update from.
     */
    public void updateData(final SmartMotorController motor) {
        if (this.setpoint == 0.0) {
            motor.stopMotor();
        } else {
            motor.set(this.setpoint);
        }
        this.distanceInches = motor.getEncoder().getDistance();
        this.velocityInchesPerSec = motor.getEncoder().getRate();
        this.currentAmps = motor.getCurrentAmps();
        this.tempC = motor.getTemperatureC();
    }
}
