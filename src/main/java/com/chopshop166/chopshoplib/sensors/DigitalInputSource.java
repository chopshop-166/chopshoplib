package com.chopshop166.chopshoplib.sensors;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * A {@link DigitalInput} that acts as a {@link BooleanSupplier}.
 */
public interface DigitalInputSource extends Sendable, BooleanSupplier {

    static DigitalInputSource wrap(final DigitalInput input) {
        return new DigitalInputSource() {

            @Override
            public boolean getAsBoolean() {
                return input.get();
            }

            @Override
            public void initSendable(SendableBuilder builder) {
                input.initSendable(builder);
            }
        };
    }

}