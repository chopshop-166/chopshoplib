package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Interface for an Encoder
 */
public interface EncoderInterface extends PIDSource, Sendable {

    /**
     * Reset the encoder distance to zero.
     * 
     * This is useful for resetting position when a known reference is reached
     */
    void reset();

    /**
     * Gets the current Distance value from the encoder.
     * 
     * @return distance traveled
     */
    double getDistance();

    /**
     * Gets the current rate of rotation.
     * 
     * @return the current rate of rotation
     */
    double getRate();

    /**
     * Determine if the counter is not moving.
     *
     * @return true if the counter has not changed for the max period
     */
    default boolean isStopped() {
        return getRate() == 0;
    }

    /**
     * Determine which direction the encoder is going.
     *
     * @return true for one direction, false for the other
     */
    default boolean isMovingForward() {
        return getRate() > 0;
    }

    static EncoderInterface wrap(final Encoder encoder) {
        return new EncoderInterface() {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource) {
                encoder.setPIDSourceType(pidSource);
            }

            @Override
            public PIDSourceType getPIDSourceType() {
                return encoder.getPIDSourceType();
            }

            @Override
            public double pidGet() {
                return encoder.pidGet();
            }

            @Override
            public String getName() {
                return encoder.getName();
            }

            @Override
            public void setName(String name) {
                encoder.setName(name);
            }

            @Override
            public String getSubsystem() {
                return encoder.getSubsystem();
            }

            @Override
            public void setSubsystem(String subsystem) {
                encoder.setSubsystem(subsystem);
            }

            @Override
            public void initSendable(SendableBuilder builder) {
                encoder.initSendable(builder);
            }

            @Override
            public void reset() {
                encoder.reset();
            }

            @Override
            public double getDistance() {
                return encoder.getDistance();
            }

            @Override
            public double getRate() {
                return encoder.getRate();
            }

            @Override
            public boolean isStopped() {
                return encoder.getStopped();
            }

            @Override
            public boolean isMovingForward() {
                return encoder.getDirection();
            }
        };
    }
}