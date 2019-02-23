package com.chopshop166.chopshoplib.triggers;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.buttons.Trigger;

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
        return new BooleanTrigger(() -> !supplier.getAsBoolean());
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