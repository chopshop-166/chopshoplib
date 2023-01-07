package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * A {@link Double} value that can be controlled via the dashboard.
 * 
 * <p>
 * Pretends to be an Analog Input for dashboard purposes.
 */
public class MockAnalogInput implements Sendable {

    /** The value of the input */
    private double value;

    /**
     * Get the value.
     * 
     * @return The assigned value.
     */
    public double get() {
        return this.value;
    }

    /**
     * Set the value.
     * 
     * @param value The value to assign.
     */
    public void set(final double value) {
        this.value = value;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Analog Input");
        builder.addDoubleProperty("Value", this::get, this::set);
    }
}