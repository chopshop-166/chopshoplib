package com.chopshop166.chopshoplib.triggers;

import java.util.function.Function;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;

/**
 * A trigger that responds to the trigger of an Xbox controller.
 */
public class XboxTrigger extends Trigger {

    /** The controller to check. */
    private final XboxController controller;

    /**
     * Create a trigger for the finger triggers.
     * 
     * @param controller The controller to use.
     * @param hand       The hand to use.
     */
    public XboxTrigger(final XboxController controller, final Hand hand) {
        super(() -> controller.getTriggerAxis(hand) > 0.5);
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
     * Create a trigger from a controller axis.
     * 
     * @param controller The controller to use.
     * @param axis       The axis to check against.
     * @param operation  The operation to run on the axis value.
     */
    public XboxTrigger(final XboxController controller, final int axis, final Function<Double, Boolean> operation) {
        super(() -> operation.apply(controller.getRawAxis(axis)));
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