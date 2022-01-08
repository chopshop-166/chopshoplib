package com.chopshop166.chopshoplib.digital;

import java.util.function.BooleanSupplier;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * A {@link DigitalInput} that acts as a {@link BooleanSupplier}.
 */
@FunctionalInterface
public interface DigitalInputSource extends Sendable, BooleanSupplier {

    @Override
    default void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Digital Input");
        builder.addBooleanProperty("Value", this::getAsBoolean, null);
    }
}