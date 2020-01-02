package com.chopshop166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * An object that is both a Sendable and a SpeedController.
 * <p>
 * Provides a single utility method to wrap an appropriate object.
 * <p>
 * This is used in a few select scenarios, when we want to keep the exact type
 * of an object general, but at the same time want to access it as one of two
 * disconnected interfaces.
 */
public interface SendableSpeedController extends Sendable, SpeedController {
    /**
     * Pass through an instance of this interface.
     * 
     * @param wrapped An already wrapped {@link SendableSpeedController}.
     * @return {@code wrapped}.
     */
    static SendableSpeedController wrap(SendableSpeedController wrapped) {
        return wrapped;
    }

    /**
     * Create an instance of this interface that dispatches to the wrapped object.
     * <p>
     * This can be used to wrap an object that implements the two base interfaces
     * separately, but not this interface.
     * 
     * @param         <T> A type that implements both {@link Sendable} and
     *                {@link SpeedController}.
     * @param wrapped An object to wrap.
     * @return A thin wrapper around {@code wrapped}.
     */
    static <T extends Sendable & SpeedController> SendableSpeedController wrap(T wrapped) {
        return new SendableSpeedController() {

            @Override
            public void initSendable(SendableBuilder builder) {
                wrapped.initSendable(builder);
            }

            @Override
            public void set(double speed) {
                wrapped.set(speed);
            }

            @Override
            public double get() {
                return wrapped.get();
            }

            @Override
            public void setInverted(boolean isInverted) {
                wrapped.setInverted(isInverted);
            }

            @Override
            public boolean getInverted() {
                return wrapped.getInverted();
            }

            @Override
            public void disable() {
                wrapped.disable();
            }

            @Override
            public void stopMotor() {
                wrapped.stopMotor();
            }

            @Override
            public void pidWrite(double output) {
                wrapped.set(output);
            }
        };
    }
}
