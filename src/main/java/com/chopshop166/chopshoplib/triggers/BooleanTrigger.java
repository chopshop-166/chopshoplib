package com.chopshop166.chopshoplib.triggers;

import java.util.function.BooleanSupplier;

import com.chopshop166.chopshoplib.RobotUtils;

import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * A trigger that responds to a function reference ({@link BooleanSupplier}).
 */
public class BooleanTrigger extends Trigger {
    private final BooleanSupplier supplier;

    /**
     * Invert the supplier function for the created trigger.
     * 
     * @param supplier The function to invert.
     * @return A trigger object.
     */
    public static BooleanTrigger not(final BooleanSupplier supplier) {
        return new BooleanTrigger(RobotUtils.not(supplier));
    }

    /**
     * Create a trigger from a function.
     * 
     * @param supplier The function to test.
     */
    public BooleanTrigger(final BooleanSupplier supplier) {
        super();
        this.supplier = supplier;
    }

    @Override
    public boolean get() {
        return supplier.getAsBoolean();
    }

}