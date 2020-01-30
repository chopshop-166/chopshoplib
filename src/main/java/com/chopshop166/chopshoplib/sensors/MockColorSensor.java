package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.util.Color;

/**
 * Mock color sensor that gets values from a dashboard.
 */
public class MockColorSensor implements IColorSensor {

    private final SendableChooser<Color> chooser = new SendableChooser<>();

    public MockColorSensor() {
        chooser.setDefaultOption("Unknown", new Color(0, 0, 0));
    }

    public void addColor(final String name, final Color color) {
        chooser.addOption(name, color);
    }

    @Override
    public Color getColor() {
        return chooser.getSelected();
    }

}