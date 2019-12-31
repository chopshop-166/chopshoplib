package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * A {@link Sendable} that behaves as a boolean value.
 */
public class MockDigitalInput implements DigitalInputSource {

    private boolean value;

    /**
     * Set the value of the "sensor".
     * 
     * @param value The new value.
     */
    public void setValue(final boolean value) {
        this.value = value;
    }

    @Override
    public boolean getAsBoolean() {
        return value;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Digital Input");
        builder.addBooleanProperty("Value", this::getAsBoolean, this::setValue);
    }
}
