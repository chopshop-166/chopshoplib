package com.chopshop166.chopshoplib.outputs;

import com.chopshop166.chopshoplib.sensors.IEncoder;
import com.chopshop166.chopshoplib.sensors.MockEncoder;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * A SpeedController with features attached.
 * 
 * This is used in a few select scenarios, when we want to keep the exact type
 * of an object general, but at the same time want to access it as one of two
 * disconnected interfaces.
 */
public interface SmartSpeedController extends Sendable, SpeedController {

    /**
     * Get the encoder attached to the robot.
     * 
     * @return An encoder, or a mock if none is attached.
     */
    default IEncoder getEncoder() {
        return new MockEncoder();
    }

    /**
     * Pass through an instance of this interface.
     * 
     * @param wrapped An already wrapped {@link SmartSpeedController}.
     * @return {@code wrapped}.
     */
    static SmartSpeedController wrap(final SmartSpeedController wrapped) {
        return wrapped;
    }

    /**
     * Create an instance of this interface that dispatches to the wrapped object.
     * <p>
     * This can be used to wrap an object that implements the two base interfaces
     * separately, but not this interface.
     * 
     * @param <T>     A type that implements both {@link Sendable} and
     *                {@link SpeedController}.
     * @param wrapped An object to wrap.
     * @return A thin wrapper around {@code wrapped}.
     */
    static <T extends Sendable & SpeedController> SmartSpeedController wrap(final T wrapped) {
        return new SmartSpeedController() {

            @Override
            public void initSendable(final SendableBuilder builder) {
                wrapped.initSendable(builder);
            }

            @Override
            public void set(final double speed) {
                wrapped.set(speed);
            }

            @Override
            public double get() {
                return wrapped.get();
            }

            @Override
            public void setInverted(final boolean isInverted) {
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
            public void pidWrite(final double output) {
                wrapped.set(output);
            }
        };
    }

    /**
     * Create an instance of this interface that dispatches to the wrapped objects.
     * <p>
     * This can be used to wrap a group of speed controllers. Motors can be passed
     * and a speed controller group is created automatically.
     * 
     * @param motor  Motor that will be wrapped, will be {@link Sendable} and put
     *               into a {@link SpeedControllerGroup}.
     * @param motors Continuation of motor.
     * @return A wrapped Speed Controller Group.
     */
    static SmartSpeedController group(final SpeedController motor, final SpeedController... motors) {
        return wrap(new SpeedControllerGroup(motor, motors));
    }
}
