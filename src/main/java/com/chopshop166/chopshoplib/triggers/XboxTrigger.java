package com.chopshop166.chopshoplib.triggers;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * A trigger that responds to a function reference ({@link BooleanSupplier}).
 */
public class XboxTrigger extends BooleanTrigger {

    private final XboxController controller;

    /**
     * Create a trigger for the finger triggers.
     * 
     * @param controller The controller to use.
     * @param hand       The hand to use.
     */
    public XboxTrigger(final XboxController controller, final Hand hand) {
        super(() -> {
            if (hand == Hand.kLeft) {
                return controller.getTriggerAxis(hand) < -0.5;
            } else {
                return controller.getTriggerAxis(hand) > 0.5;
            }
        });
        this.controller = controller;
    }

    /**
     * Create a trigger from a controller axis.
     * 
     * @param controller The controller to use.
     * @param axis       The axis to check against.
     * @param positive   True if the value should be greater than 0.5, false if it
     *                   should be less than -0.5
     */
    public XboxTrigger(final XboxController controller, final int axis, final boolean positive) {
        super(() -> {
            if (positive) {
                return controller.getRawAxis(axis) > 0.5;
            } else {
                return controller.getRawAxis(axis) < -0.5;
            }
        });
        this.controller = controller;
    }

    /**
     * Get the controller used by this trigger.
     * 
     * @return The XBox Controller object.
     */
    public XboxController getController() {
        return controller;
    }

}