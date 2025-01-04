package com.chopshop166.chopshoplib.motors;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkMax;
import com.chopshop166.chopshoplib.sensors.SparkMaxEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkRelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

/**
 * CSSparkMax
 *
 * Derives SmartMotorController to allow for use on a SparkMax Motor Controller. It will act as a
 * normal SparkMax with encoders, but will also be able to use PID.
 */
public class CSSparkMax extends CSSpark {

    /**
     * Create a Spark MAX associated with a brushed motor.
     * 
     * @param deviceID The CAN device ID
     * @param countsPerRev The counts per revolution of the encoder.
     * @return A CSSparkMax object.
     */
    public static CSSparkMax brushed(final int deviceID, final int countsPerRev) {
        final var spark = new SparkMax(deviceID, MotorType.kBrushed);
        final var config = new SparkMaxConfig();
        config.encoder.countsPerRevolution(countsPerRev);
        spark.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        return new CSSparkMax(spark);
    }

    /**
     * Create a Spark MAX associated with a Neo or Neo 550 motor.
     * 
     * @param deviceID The CAN device ID
     * @return A CSSparkMax object.
     */
    public static CSSparkMax neo(final int deviceID) {
        final var spark = new SparkMax(deviceID, MotorType.kBrushless);
        return new CSSparkMax(spark);
    }

    /**
     * Create a SPARK Max Encoder set up for a Neo (most common behavior).
     * 
     * @param deviceID The device ID.
     */
    public CSSparkMax(final int deviceID) {
        this(new SparkMax(deviceID, MotorType.kBrushless));
    }

    /**
     * Helper contructor to get a Neo encoder out of a spark.
     * 
     * @param spark The motor controller.
     */
    private CSSparkMax(final SparkMax spark) {
        super(spark, new SparkMaxEncoder(spark));
    }

    @Override
    public SparkMaxEncoder getEncoder() {
        // This cast is safe because we're the ones setting it in the first place.
        return (SparkMaxEncoder) super.getEncoder();
    }

    @Override
    public SparkMax getMotorController() {
        return (SparkMax) super.getMotorController();
    }

    @Override
    public String getMotorControllerType() {
        return "Spark Max";
    }
}
