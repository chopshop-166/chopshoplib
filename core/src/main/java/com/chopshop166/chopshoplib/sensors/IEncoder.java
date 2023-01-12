package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.util.sendable.Sendable;

/**
 * Interface for an Encoder
 */
public interface IEncoder extends Sendable, IAbsolutePosition {

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
     * Gets the absolute position in rotations.
     * 
     * @return Rotations.
     */
    @Override
    default double getAbsolutePosition() {
        return 0;
    }

    /**
     * Determine if the counter is not moving.
     *
     * @return true if the counter has not changed for the max period
     */
    default boolean isStopped() {
        return this.getRate() == 0;
    }

    /**
     * Determine which direction the encoder is going.
     *
     * @return true for one direction, false for the other
     */
    default boolean isMovingForward() {
        return this.getRate() > 0;
    }

}
