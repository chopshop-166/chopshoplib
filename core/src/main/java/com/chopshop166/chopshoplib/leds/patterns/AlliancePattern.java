package com.chopshop166.chopshoplib.leds.patterns;

import java.util.Optional;
import com.chopshop166.chopshoplib.leds.Pattern;
import com.chopshop166.chopshoplib.leds.SegmentBuffer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.util.Color;

/** Pattern for displaying the robot's alliance color. */
public class AlliancePattern extends Pattern {
    @Override
    public void initialize(final SegmentBuffer buffer) {
        final Optional<Alliance> alliance = DriverStation.getAlliance();
        if (alliance.isEmpty()) {
            buffer.setAll(Color.kBlack);
        } else if (alliance.get() == Alliance.Blue) {
            buffer.setAll(new Color(2, 15, 250));
        } else {
            buffer.setAll(new Color(250, 2, 2));
        }
    }

    @Override
    public String toString() {
        return "AlliancePattern()";
    }
}
