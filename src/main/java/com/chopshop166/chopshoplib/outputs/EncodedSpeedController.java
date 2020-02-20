package com.chopshop166.chopshoplib.outputs;

import com.chopshop166.chopshoplib.sensors.IEncoder;
import com.chopshop166.chopshoplib.sensors.SparkMaxEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * {@link SpeedController} that also acts as an {@link IEncoder}.
 */
public interface EncodedSpeedController extends SendableSpeedController, IEncoder {

    /**
     * Create an {@link EncodedSpeedController} out of a {@link CANSparkMax}.
     * 
     * The Spark Max has a built in encoder. For ease of use, this relies on the
     * existing {@link #join(SendableSpeedController, IEncoder)} to create a wrapped
     * object.
     * 
     * @param spark A Spark Max object with an associated encoder.
     * @return A wrapped object.
     */
    static EncodedSpeedController wrap(final CANSparkMax spark) {
        return join(new SparkMaxSendable(spark), new SparkMaxEncoder(spark.getEncoder()));
    }

    /**
     * Combine a speed controller and an encoder into a single object.
     * 
     * @param sc  The speed controller.
     * @param enc The encoder.
     * @return A wrapped object.
     */
    static EncodedSpeedController join(final SendableSpeedController sc, final IEncoder enc) {
        return new EncodedSpeedController() {

            @Override
            public void initSendable(SendableBuilder builder) {
                enc.initSendable(builder);
                sc.initSendable(builder);
            }

            @Override
            public void set(double speed) {
                sc.set(speed);
            }

            @Override
            public double get() {
                return sc.get();
            }

            @Override
            public void setInverted(boolean isInverted) {
                sc.setInverted(isInverted);
            }

            @Override
            public boolean getInverted() {
                return sc.getInverted();
            }

            @Override
            public void disable() {
                sc.disable();
            }

            @Override
            public void stopMotor() {
                sc.stopMotor();
            }

            @Override
            public void reset() {
                enc.reset();
            }

            @Override
            public double getDistance() {
                return enc.getDistance();
            }

            @Override
            public double getRate() {
                return enc.getRate();
            }

            @Override
            public void pidWrite(double output) {
                sc.set(output);
            }
        };
    }
}
