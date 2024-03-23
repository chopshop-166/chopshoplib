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
            buffer.setAll(Color.kBlue);
        } else {
            buffer.setAll(Color.kRed);
        }
    }

    @Override
    public String toString() {
        return "AlliancePattern()";
    }
}
