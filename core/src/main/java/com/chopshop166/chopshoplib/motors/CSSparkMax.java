package com.chopshop166.chopshoplib.motors;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

/**
 * CSSparkMax
 *
 * Derives SmartMotorController to allow for use on a SparkMax Motor Controller. It will act as a
 * normal SparkMax with encoders, but will also be able to use PID.
 */
public class CSSparkMax extends CSSpark {

    /**
     * Create a new wrapped SPARK MAX Controller.
     *
     * @param deviceID The device ID.
     * @param type The motor type connected to the controller. Brushless motors must be connected to
     *        their matching color and the hall sensor plugged in. Brushed motors must be connected
     *        to the Red and Black terminals only.
     */
    public CSSparkMax(final int deviceID, final MotorType type) {
        super(new CANSparkMax(deviceID, type), type);
    }

    @Override
    public CANSparkMax getMotorController() {
        return (CANSparkMax) super.getMotorController();
    }

    @Override
    public String getMotorControllerType() {
        return "Spark Max";
    }
}
