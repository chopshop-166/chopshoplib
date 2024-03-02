package com.chopshop166.chopshoplib.motors;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkRelativeEncoder;

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
        final var spark = new CANSparkMax(deviceID, MotorType.kBrushed);
        final var enc = spark.getEncoder(SparkRelativeEncoder.Type.kQuadrature, countsPerRev);
        return new CSSparkMax(spark, enc);
    }

    /**
     * Create a Spark MAX associated with a Neo or Neo 550 motor.
     * 
     * @param deviceID The CAN device ID
     * @return A CSSparkMax object.
     */
    public static CSSparkMax neo(final int deviceID) {
        final var spark = new CANSparkMax(deviceID, MotorType.kBrushless);
        return new CSSparkMax(spark, getNeoEncoder(spark));
    }

    /**
     * Create a Spark MAX associated with a Neo Vortex motor.
     * 
     * @param deviceID The CAN device ID
     * @return A CSSparkMax object.
     */
    public static CSSparkMax vortex(final int deviceID) {
        final var spark = new CANSparkMax(deviceID, MotorType.kBrushless);
        return new CSSparkMax(spark, getVortexEncoder(spark));
    }

    /**
     * Create a new wrapped SPARK MAX Controller.
     *
     * @param spark The spark object to wrap.
     * @param enc The encoder object.
     */
    public CSSparkMax(final CANSparkMax spark, final RelativeEncoder enc) {
        super(spark, enc);
    }

    /**
     * Create a SPARK Max Encoder set up for a Neo (most common behavior).
     * 
     * @param deviceID The device ID.
     */
    public CSSparkMax(final int deviceID) {
        this(new CANSparkMax(deviceID, MotorType.kBrushless));
    }

    /**
     * Helper contructor to get a Neo encoder out of a spark.
     * 
     * @param spark The motor controller.
     */
    private CSSparkMax(final CANSparkMax spark) {
        this(spark, getNeoEncoder(spark));
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
