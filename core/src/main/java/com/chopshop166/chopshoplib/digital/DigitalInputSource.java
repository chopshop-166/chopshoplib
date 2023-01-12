package com.chopshop166.chopshoplib.digital;

import java.util.function.BooleanSupplier;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * A wrapper for {@link BooleanSupplier} that can be sent to the dashboard.
 */
@FunctionalInterface
public interface DigitalInputSource extends Sendable, BooleanSupplier {

    @Override
    default void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Digital Input");
        builder.addBooleanProperty("Value", this::getAsBoolean, null);
    }
}
