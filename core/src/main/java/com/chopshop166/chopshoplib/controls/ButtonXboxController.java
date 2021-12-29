package com.chopshop166.chopshoplib.controls;

import java.util.EnumMap;
import java.util.Map;

import com.chopshop166.chopshoplib.triggers.AxisButton;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * Represents an XBox controller along with its associated buttons.
 */
public class ButtonXboxController extends XboxController {

    /**
     * Enum of POV HAT directions.
     */
    public enum POVDirection {
        UP(0), UP_RIGHT(45), RIGHT(90), DOWN_RIGHT(135), DOWN(180), DOWN_LEFT(225), LEFT(270), UP_LEFT(315);

        /** The angle of the direction enum. */
        private int dPadRotation;

        // Returns an integer representing the angle on the POV.
        private int getAngle() {
            return this.dPadRotation;
        }

        // Constructor.
        POVDirection(final int rotation) {
            this.dPadRotation = rotation;
        }
    }

    /** The mapping of button ID to command button. */
    private final Map<Button, JoystickButton> buttons = new EnumMap<>(Button.class);

    /** The mapping of POV Button direction to command button. */
    private final Map<POVDirection, POVButton> povButtons = new EnumMap<>(POVDirection.class);

    /** The mapping of axis to command button. */
    private final Map<Axis, AxisButton> triggerButtons = new EnumMap<>(Axis.class);

    /**
     * Construct an instance of a Xbox Controller along with each button the
     * joystick has.
     *
     * @param port The USB port that the Xbox Controller is connected to on the
     *             Driver Station.
     */
    public ButtonXboxController(final int port) {
        super(port);
    }

    /**
     * Get the right trigger - left trigger
     * 
     * @return A double in {@code [-1, 1]}
     */
    public double getTriggers() {
        return getRightTriggerAxis() - getLeftTriggerAxis();
    }

    /**
     * Get a button from this Xbox Controller.
     * <p>
     * Returns the specified button of a Xbox Controller without having to
     * explicitly create each button.
     * 
     * @param buttonId The index of the button to access.
     * @return The button object for the given ID.
     */
    public JoystickButton getButton(final Button buttonId) {
        return buttons.computeIfAbsent(buttonId, b -> new JoystickButton(this, b.value));
    }

    /**
     * Get an axis from this Xbox Controller.
     * <p>
     * Returns the specified trigger of a Xbox Controller without having to
     * explicitly create each one.
     * 
     * @param axis The axis to access.
     * @return The trigger object for the given hand.
     */
    public AxisButton getAxis(final Axis axis) {
        return triggerButtons.computeIfAbsent(axis, h -> new AxisButton(this, axis));
    }

    /**
     * Get a button from the POV hat on this Xbox Controller.
     * <p>
     * Returns the specified POV Hat button of an Xbox controller without having to
     * explicitly create each button.
     * 
     * @param angle The index of the button to access.
     * @return The button object for the given ID.
     */
    public POVButton getPovButton(final POVDirection angle) {
        return povButtons.computeIfAbsent(angle, a -> new POVButton(this, a.getAngle()));
    }
}
