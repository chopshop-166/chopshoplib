package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.util.Color;

/**
 * Mock color sensor that gets values from a dashboard.
 */
public class MockColorSensor implements IColorSensor {

    /** The color chooser to use */
    private final SendableChooser<Color> chooser = new SendableChooser<>();

    /** Create the chooser */
    public MockColorSensor() {
        chooser.setDefaultOption("Unknown", new Color(0, 0, 0));
    }

    /**
     * Add a color to the chooser.
     * 
     * @param name  The name.
     * @param color The color.
     */
    public void addColor(final String name, final Color color) {
        chooser.addOption(name, color);
    }

    @Override
    public Color getColor() {
        return chooser.getSelected();
    }

}