package com.chopshop166.chopshoplib.leds;

import org.littletonrobotics.junction.Logger;
import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;
import com.chopshop166.chopshoplib.leds.patterns.SolidColorPattern;
import com.chopshop166.chopshoplib.maps.LedMapBase;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

/** A base class for an LED subsystem. */
@SuppressWarnings({"PMD.LinguisticNaming"})
public class LEDSubsystem extends SmartSubsystemBase {

    /** The LED subsystem map. */
    protected final LedMapBase map;
    /**
     * The segment buffer that we use.
     * 
     * Default to a length of 60, start empty output
     * 
     * We reuse this object. Length is expensive to set, so only set it once, then just update data.
     */
    protected final LEDStripBuffer ledBuffer;

    /**
     * Constructor.
     * 
     * @param map The subsystem map to use.
     */
    public LEDSubsystem(final LedMapBase map) {
        super();
        this.map = map;
        this.ledBuffer = map.ledBuffer;
    }

    /**
     * Generic "set a tag to a color" command.
     * 
     * @param tag The tag to set.
     * @param color The solid color to set the tag to.
     * @return A command object.
     */
    public Command setPattern(final String tag, final Color color) {
        return this.setPattern(tag, new SolidColorPattern(color));
    }

    /**
     * Generic "set a tag to a pattern" command.
     * 
     * @param tag The tag to set.
     * @param pattern The pattern to set it to.
     * @return A command object.
     */
    public Command setPattern(final String tag, final Pattern pattern) {
        return Commands.runOnce(() -> {
            this.ledBuffer.setPattern(tag, pattern);
        }).ignoringDisable(true);
    }

    /**
     * Generic "set a tag to a color" command.
     * 
     * @param tag The tag to set.
     * @param color The solid color to set the tag to.
     * @param indicator The name to record in the log.
     * @return A command object.
     */
    public Command setPattern(final String tag, final Color color, final String indicator) {
        return this.setPattern(tag, new SolidColorPattern(color), indicator);
    }

    /**
     * Generic "set a tag to a pattern" command.
     * 
     * @param tag The tag to set.
     * @param pattern The pattern to set it to.
     * @param indicator The name to record in the log.
     * @return A command object.
     */
    public Command setPattern(final String tag, final Pattern pattern, final String indicator) {
        return this.setPattern(tag, pattern).andThen(this.logIndicator(indicator));
    }

    /**
     * Generic "set everything to a color" command.
     * 
     * @param color The solid color to set the buffer to.
     * @return A command object.
     */
    public Command setGlobalPattern(final Color color) {
        return this.setGlobalPattern(new SolidColorPattern(color));
    }

    /**
     * Generic "set everything to a pattern" command.
     * 
     * @param pattern The pattern to set it to.
     * @return A command object.
     */
    @SuppressWarnings({"PMD.LinguisticNaming"})
    public Command setGlobalPattern(final Pattern pattern) {
        return Commands.runOnce(() -> {
            this.ledBuffer.setGlobalPattern(pattern);
        }).ignoringDisable(true);
    }

    /**
     * Log a message saying what the indicator lights are doing.
     * 
     * @param name The name of the pattern.
     * @return A command object.
     */
    public Command logIndicator(final String name) {
        return Commands.runOnce(() -> {
            Logger.recordOutput("IndicateLEDs", name);
        }).ignoringDisable(true);
    }

    @Override
    public void reset() {
        // Nothing to reset here
    }

    @Override
    public void safeState() {
        // LEDs are rather safe
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // Use this for any background processing
        this.ledBuffer.update(this.map);
    }
}
