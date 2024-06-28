package com.chopshop166.chopshoplib.controls;

import java.util.EnumMap;
import java.util.Map;
import com.chopshop166.chopshoplib.motors.Modifier;
import com.chopshop166.chopshoplib.triggers.AxisButton;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * Represents an XBox controller along with its associated buttons.
 */
public class ButtonXboxController extends CommandXboxController {

    /**
     * Enum of POV HAT directions.
     */
    public enum POVDirection {
        /** Up. */
        UP(0),
        /** Up right. */
        UP_RIGHT(45),
        /** Right. */
        RIGHT(90),
        /** Down right. */
        DOWN_RIGHT(135),
        /** Down. */
        DOWN(180),
        /** Down left. */
        DOWN_LEFT(225),
        /** Left. */
        LEFT(270),
        /** Up left. */
        UP_LEFT(315);

        /** The angle of the direction enum. */
        private final int dPadRotation;

        // Returns an integer representing the angle on the POV.
        private int getAngle() {
            return this.dPadRotation;
        }

        // Constructor.
        POVDirection(final int rotation) {
            this.dPadRotation = rotation;
        }
    }

    /** The mapping of POV Button direction to command button. */
    private final Map<POVDirection, POVButton> povButtons = new EnumMap<>(POVDirection.class);

    /** The mapping of axis to command button. */
    private final Map<Axis, AxisButton> triggerButtons = new EnumMap<>(Axis.class);

    /** The deadband range. */
    private final Modifier deadband;

    /**
     * Construct an instance of a Xbox Controller along with each button the joystick has.
     *
     * @param port The USB port that the Xbox Controller is connected to on the Driver Station.
     */
    public ButtonXboxController(final int port) {
        this(port, 0.05);
    }

    /**
     * Construct an instance of a Xbox Controller along with each button the joystick has.
     *
     * @param port The USB port that the Xbox Controller is connected to on the Driver Station.
     * @param dbRange The dead band range.
     */
    public ButtonXboxController(final int port, final double dbRange) {
        super(port);
        this.deadband = Modifier.scalingDeadband(dbRange);
    }

    /**
     * Get the deadbanded X axis of the left joystick.
     * 
     * @return The deadbanded value.
     */
    public double getDbLeftX() {
        return this.deadband.applyAsDouble(this.getLeftX());
    }

    /**
     * Get the deadbanded Y axis of the left joystick.
     * 
     * @return The deadbanded value.
     */
    public double getDbLeftY() {
        return this.deadband.applyAsDouble(this.getLeftY());
    }

    /**
     * Get the deadbanded X axis of the right joystick.
     * 
     * @return The deadbanded value.
     */
    public double getDbRightX() {
        return this.deadband.applyAsDouble(this.getRightX());
    }

    /**
     * Get the deadbanded Y axis of the right joystick.
     * 
     * @return The deadbanded value.
     */
    public double getDbRightY() {
        return this.deadband.applyAsDouble(this.getRightY());
    }

    /**
     * Get the right trigger - left trigger
     * 
     * @return A double in {@code [-1, 1]}
     */
    public double getTriggers() {
        return this.getRightTriggerAxis() - this.getLeftTriggerAxis();
    }

    /**
     * Get the POV Up button.
     * 
     * @return A joystick button.
     */
    @Override
    public POVButton povUp() {
        return this.getPovButton(POVDirection.UP);
    }

    /**
     * Get the POV Up Right button.
     * 
     * @return A joystick button.
     */
    @Override
    public POVButton povUpRight() {
        return this.getPovButton(POVDirection.UP_RIGHT);
    }

    /**
     * Get the POV Right button.
     * 
     * @return A joystick button.
     */
    @Override
    public POVButton povRight() {
        return this.getPovButton(POVDirection.RIGHT);
    }

    /**
     * Get the POV Down Right button.
     * 
     * @return A joystick button.
     */
    @Override
    public POVButton povDownRight() {
        return this.getPovButton(POVDirection.DOWN_RIGHT);
    }

    /**
     * Get the POV Down button.
     * 
     * @return A joystick button.
     */
    @Override
    public POVButton povDown() {
        return this.getPovButton(POVDirection.DOWN);
    }

    /**
     * Get the POV Down Left button.
     * 
     * @return A joystick button.
     */
    @Override
    public POVButton povDownLeft() {
        return this.getPovButton(POVDirection.DOWN_LEFT);
    }

    /**
     * Get the POV Left button.
     * 
     * @return A joystick button.
     */
    @Override
    public POVButton povLeft() {
        return this.getPovButton(POVDirection.LEFT);
    }

    /**
     * Get the POV Up Left button.
     * 
     * @return A joystick button.
     */
    @Override
    public POVButton povUpLeft() {
        return this.getPovButton(POVDirection.UP_LEFT);
    }

    /**
     * Get an axis from this Xbox Controller.
     * <p>
     * Returns the specified trigger of a Xbox Controller without having to explicitly create each
     * one.
     * 
     * @param axis The axis to access.
     * @return The trigger object for the given hand.
     */
    public AxisButton getAxis(final Axis axis) {
        return this.triggerButtons.computeIfAbsent(axis, h -> new AxisButton(this.getHID(), h));
    }

    /**
     * Get a button from the POV hat on this Xbox Controller.
     * <p>
     * Returns the specified POV Hat button of an Xbox controller without having to explicitly
     * create each button.
     * 
     * @param angle The index of the button to access.
     * @return The button object for the given ID.
     */
    public POVButton getPovButton(final POVDirection angle) {
        return this.povButtons.computeIfAbsent(angle,
                a -> new POVButton(this.getHID(), a.getAngle()));
    }
}
