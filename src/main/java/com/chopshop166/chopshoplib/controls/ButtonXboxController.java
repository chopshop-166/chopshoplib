package com.chopshop166.chopshoplib.controls;

import java.util.EnumMap;
import java.util.Map;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * Represents an XBox controller along with its associated buttons.
 */
public class ButtonXboxController extends XboxController {

    /** The mapping of button ID to command button. */
    private final Map<Button, JoystickButton> buttons = new EnumMap<>(Button.class);

    /** The mapping of POV Button direction and command button */
    private final Map<Direction, POVButton> povButtons = new EnumMap<>(Direction.class);

    /**
     * Construct an instance of a Xbox Controller along with each button the
     * joystick has.
     *
     * @param port The USB port that the Xbox Controller is connected to on the
     *             Driver Station
     */
    public ButtonXboxController(final int port) {
        super(port);
    }

    /**
     * Get a button from this Xbox Controller
     * <p>
     * Returns the sepcified button of a Xbox Controller without having to
     * explicitly create each button.
     * 
     * @param buttonId The index of the button to accesss
     * @return The button object for the given ID
     */
    public JoystickButton getButton(final Button buttonId) {
        if (!buttons.containsKey(buttonId)) {
            buttons.put(buttonId, new JoystickButton(this, buttonId.value));
        }
        return buttons.get(buttonId);
    }

    /**
     * Get the right trigger - left trigger
     * 
     * @return A double in {@code [-1, 1]}
     */
    public double getTriggers() {
        final double kRight = getTriggerAxis(Hand.kRight);
        final double kLeft = getTriggerAxis(Hand.kLeft);
        return kRight - kLeft;
    }

    /**
     * Get a button from the POV hat on this Xbox Controller
     * <p>
     * Returns the sepcified POV Hat button of an Xbox controller without having to
     * explicitly create each button.
     * 
     * @param angle The index of the button to accesss
     * @return The button object for the given ID
     */
    public POVButton getPovButton(final Direction angle) {
        if (!povButtons.containsKey(angle)) {
            povButtons.put(angle, new POVButton(this, angle.getAngle()));
        }
        return povButtons.get(angle);
    }

    /**
     * Enum of POV HAT directions
     */
    public enum Direction {
        Up(0), UpRight(45), Right(90), DownRight(135), Down(180), DownLeft(225), Left(270), LeftUp(315);

        /** The angle of the direction enum */
        private int dPadRotation;

        // Returning an interger to compare whether we're in the right place or not
        private int getAngle() {
            return this.dPadRotation;
        }

        // Returning the level the lift is at (top middle or bottom)
        Direction(final int rotation) {
            this.dPadRotation = rotation;
        }
    }
}
