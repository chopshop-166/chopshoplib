package com.chopshop166.chopshoplib.motors;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkRelativeEncoder;

/**
 * CSSparkFlex
 *
 * Derives SmartMotorController to allow for use on a SparkFlex Motor Controller. It will act as a
 * normal SparkFlex with encoders, but will also be able to use PID.
 */
public class CSSparkFlex extends CSSpark {

    /**
     * Create a Spark FLEX associated with a brushed motor.
     * 
     * @param deviceID The CAN device ID
     * @param countsPerRev The counts per revolution of the encoder.
     * @return A CSSparkFlex object.
     */
    public static CSSparkFlex brushed(final int deviceID, final int countsPerRev) {
        final var spark = new CANSparkFlex(deviceID, MotorType.kBrushed);
        final var enc = spark.getEncoder(SparkRelativeEncoder.Type.kQuadrature, countsPerRev);
        return new CSSparkFlex(spark, enc);
    }

    /**
     * Create a Spark FLEX associated with a Neo or Neo 550 motor.
     * 
     * @param deviceID The CAN device ID
     * @return A CSSparkFlex object.
     */
    public static CSSparkFlex neo(final int deviceID) {
        final var spark = new CANSparkFlex(deviceID, MotorType.kBrushless);
        final var enc = spark.getEncoder(SparkRelativeEncoder.Type.kHallSensor, 42);
        return new CSSparkFlex(spark, enc);
    }

    /**
     * Create a Spark FLEX associated with a Neo Vortex motor.
     * 
     * @param deviceID The CAN device ID
     * @return A CSSparkFlex object.
     */
    public static CSSparkFlex vortex(final int deviceID) {
        final var spark = new CANSparkFlex(deviceID, MotorType.kBrushless);
        final var enc = spark.getEncoder(SparkRelativeEncoder.Type.kQuadrature, 7168);
        return new CSSparkFlex(spark, enc);
    }

    /**
     * Create a new wrapped SPARK FLEX Controller.
     *
     * @param spark The spark object to wrap.
     * @param enc The encoder object.
     */
    public CSSparkFlex(final CANSparkFlex spark, final RelativeEncoder enc) {
        super(spark, enc);
    }

    @Override
    public CANSparkFlex getMotorController() {
        return (CANSparkFlex) super.getMotorController();
    }

    @Override
    public String getMotorControllerType() {
        return "Spark Flex";
    }
}
