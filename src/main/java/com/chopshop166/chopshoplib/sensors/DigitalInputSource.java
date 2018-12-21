package com.chopshop166.chopshoplib.sensors;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public interface DigitalInputSource extends Sendable, BooleanSupplier {

    static DigitalInputSource wrap(final DigitalInput input) {
        return new DigitalInputSource() {

            @Override
            public boolean getAsBoolean() {
                return input.get();
            }

            @Override
            public void setSubsystem(String subsystem) {
                input.setSubsystem(subsystem);
            }

            @Override
            public void setName(String name) {
                input.setName(name);
            }

            @Override
            public void initSendable(SendableBuilder builder) {
                input.initSendable(builder);
            }

            @Override
            public String getSubsystem() {
                return input.getSubsystem();
            }

            @Override
            public String getName() {
                return input.getName();
            }
        };
    }

}