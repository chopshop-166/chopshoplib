package com.chopshop166.chopshoplib.motors;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;

/**
 * CSSparkFlex
 *
 * Derives SmartMotorController to allow for use on a SparkFlex Motor Controller. It will act as a
 * normal SparkFlex with encoders, but will also be able to use PID.
 */
public class CSSparkFlex extends CSSpark {

    /**
     * Create a new wrapped SPARK Flex Controller.
     *
     * @param deviceID The device ID.
     * @param type The motor type connected to the controller. Brushless motors must be connected to
     *        their matching color and the hall sensor plugged in. Brushed motors must be connected
     *        to the Red and Black terminals only.
     */
    public CSSparkFlex(final int deviceID, final MotorType type) {
        super(new CANSparkFlex(deviceID, type), type);
    }

    @Override
    public CANSparkFlex getMotorController() {
        return (CANSparkFlex) super.getMotorController();
    }
}
