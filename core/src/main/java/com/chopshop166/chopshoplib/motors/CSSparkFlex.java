package com.chopshop166.chopshoplib.motors;

import com.chopshop166.chopshoplib.sensors.SparkFlexEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

/**
 * CSSparkFlex
 *
 * Derives SmartMotorController to allow for use on a SparkFlex Motor Controller. It will act as a
 * normal SparkFlex with encoders, but will also be able to use PID.
 */
public class CSSparkFlex extends CSSpark {

    /**
     * Create a SPARK Max Encoder set up for a Vortex (most common behavior).
     * 
     * @param deviceID The device ID.
     */
    public CSSparkFlex(final int deviceID) {
        this(new SparkFlex(deviceID, MotorType.kBrushless));
    }

    /**
     * Helper contructor to get a Vortex encoder out of a spark.
     * 
     * @param spark The motor controller.
     */
    public CSSparkFlex(final SparkFlex spark) {
        super(spark, new SparkFlexEncoder(spark));
    }

    @Override
    public SparkFlexEncoder getEncoder() {
        // This cast is safe because we're the ones setting it in the first place.
        return (SparkFlexEncoder) super.getEncoder();
    }

    @Override
    public SparkFlex getMotorController() {
        return (SparkFlex) super.getMotorController();
    }

    @Override
    public String getMotorControllerType() {
        return "Spark Flex";
    }

    @Override
    public void setInverted(final boolean isInverted) {
        final var config = new SparkFlexConfig();
        config.inverted(isInverted);
        getMotorController().configure(config, ResetMode.kNoResetSafeParameters,
                PersistMode.kPersistParameters);
    }
}
