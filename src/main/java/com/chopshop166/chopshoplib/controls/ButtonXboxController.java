package com.chopshop166.chopshoplib.controls;

import java.util.EnumMap;
import java.util.Map;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * Represents an XBox controller along with its associated buttons.
 */
public class ButtonXboxController extends XboxController {
    private final Map<Button, JoystickButton> buttons = new EnumMap<>(Button.class);

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
    public JoystickButton getButton(final Button buttonId) {
        if (!buttons.containsKey(buttonId)) {
            buttons.put(buttonId, new JoystickButton(this, buttonId.value));
        }
        return buttons.get(buttonId);
    }

    public double getTriggers() {
        final double kRight = getTriggerAxis(Hand.kRight);
        final double kLeft = getTriggerAxis(Hand.kLeft);
        return kRight - kLeft;
    }

}
