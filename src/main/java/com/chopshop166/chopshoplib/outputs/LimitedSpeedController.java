package com.chopshop166.chopshoplib.outputs;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * {@link SpeedController} that stops when a condition occurs.
 *
 * This is useful for situations when a drive needs to be limited based on a
 * switch.
 * <p>
 * Some speed controllers have this behavior built in, but this wrapper exists
 * to add support for controllers that do not.
 */
public class LimitedSpeedController implements SendableSpeedController {

    final private SendableSpeedController wrapped;
    final private BooleanSupplier posLimit;
    final private BooleanSupplier negLimit;

    /**
     * Always return false - the default state for "not pressed".
     * 
     * @return {@code false}, always
     */
    private static boolean alwaysFalse() {
        return false;
    }

    /**
     * Limit just the positive direction of travel.
     * 
     * @param wrapped  The speed controller to limit.
     * @param posLimit The limiting condition.
     * @return The limited speed controller.
     */
    public static LimitedSpeedController limitPositive(final SendableSpeedController wrapped,
            final BooleanSupplier posLimit) {
        return new LimitedSpeedController(wrapped, posLimit, LimitedSpeedController::alwaysFalse);
    }

    /**
     * Limit just the negative direction of travel.
     * 
     * @param wrapped  The speed controller to limit.
     * @param negLimit The limiting condition.
     * @return The limited speed controller.
     */
    public static LimitedSpeedController limitNegative(final SendableSpeedController wrapped,
            final BooleanSupplier negLimit) {
        return new LimitedSpeedController(wrapped, LimitedSpeedController::alwaysFalse, negLimit);
    }

    /**
     * Wrap a speed controller in limits.
     * 
     * @param wrapped  The speed controller to limit.
     * @param posLimit The limiting condition for the positive direction.
     * @param negLimit The limiting condition for the negative direction.
     */
    public LimitedSpeedController(final SendableSpeedController wrapped, final BooleanSupplier posLimit,
            final BooleanSupplier negLimit) {
        super();
        this.wrapped = wrapped;
        this.posLimit = posLimit == null ? LimitedSpeedController::alwaysFalse : posLimit;
        this.negLimit = negLimit == null ? LimitedSpeedController::alwaysFalse : negLimit;
    }

    /**
     * Get the original speed controller.
     * 
     * @return The wrapped object.
     */
    public SendableSpeedController getWrapped() {
        return wrapped;
    }

    @Override
    public void set(final double speed) {
        if (speed > 0.0 && posLimit.getAsBoolean() || speed < 0.0 && negLimit.getAsBoolean()) {
            wrapped.set(0.0);
        } else {
            wrapped.set(speed);
        }
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

    @Override
    public void initSendable(final SendableBuilder builder) {
        wrapped.initSendable(builder);
        builder.addBooleanProperty("Positive Limit", posLimit::getAsBoolean, null);
        builder.addBooleanProperty("Negative Limit", negLimit::getAsBoolean, null);
    }
}
