package com.chopshop166.chopshoplib.controls;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * Represents an XBox controller along with its associated buttons.
 */
public class ButtonXboxController extends XboxController {
    private final Map<Integer, Button> buttons = new HashMap<>();

    /**
     * Construct an instance of a joystick along with each button the joystick has.
     *
     * @param port The USB port that the joystick is connected to on the Driver
     *             Station
     */
    public ButtonXboxController(final int port) {
        super(port);
    }

    /**
     * Get a button from this joystick
     * <p>
     * Returns the sepcified button of a joystick without having to explicitly
     * create each button.
     * 
     * @param buttonId The index of the button to accesss
     * @return The button object for the given ID
     */
    public Button getButton(final int buttonId) {
        if (!buttons.containsKey(buttonId)) {
            buttons.put(buttonId, new JoystickButton(this, buttonId));
        }
        return buttons.get(buttonId);
    }

    public double getTriggers() {

        double kRight = getTriggerAxis(Hand.kRight);
        double kLeft = getTriggerAxis(Hand.kLeft);
        double compositeTriggerValue = kRight - kLeft;

        return compositeTriggerValue;
    }

    /**
     * Get a button from this joystick
     * <p>
     * Returns the sepcified button of a joystick without having to explicitly
     * create each button.
     * 
     * @param buttonId The index of the button to accesss
     * @return The button object for the given ID
     */
    public Button getButton(final XBoxButton buttonId) {
        return getButton(buttonId.get());
    }

    /**
     * Public implementation of the enum from {@link XboxController}
     */
    public enum XBoxButton {
        BUMPER_LEFT(5), BUMPER_RIGHT(6), STICK_LEFT(9), STICK_RIGHT(10), A(1), B(2), X(3), Y(4), BACK(7), START(8);

        private final int value;

        XBoxButton(final int value) {
            this.value = value;
        }

        public int get() {
            return value;
        }
    }

}
