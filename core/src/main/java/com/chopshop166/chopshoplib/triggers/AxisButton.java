package com.chopshop166.chopshoplib.triggers;

import java.util.function.DoublePredicate;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * A button that responds to an axis of an Xbox controller.
 */
public class AxisButton extends Trigger {

    /** The controller to check. */
    private final XboxController controller;

    /**
     * Create a button based on an axis.
     * 
     * @param controller The controller to use.
     * @param axis       The axis to use.
     * @param predicate  The check against the axis value.
     */
    public AxisButton(final XboxController controller, final Axis axis, final DoublePredicate predicate) {
        super(() -> predicate.test(controller.getRawAxis(axis.value)));
        this.controller = controller;
    }

    /**
     * Create a button based on an axis.
     * 
     * @param controller The controller to use.
     * @param axis       The axis to use.
     */
    public AxisButton(final XboxController controller, final Axis axis) {
        this(controller, axis, v -> v > 0.5);
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