package com.chopshop166.chopshoplib.commands;

import org.littletonrobotics.junction.inputs.LoggableInputs;
import com.chopshop166.chopshoplib.logging.LoggableMap;
import com.chopshop166.chopshoplib.logging.LoggedSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * A {@link SmartSubsystem} that goes to specific presets.
 */
public abstract class PresetSubsystem<P extends Enum<P>, D extends LoggableInputs, M extends LoggableMap<D>>
        extends LoggedSubsystem<D, M> {

    protected PresetSubsystem(final D data, final M map) {
        super(data, map);
    }

    abstract Command moveToPreset(final P preset);

    abstract Command zero();
}
