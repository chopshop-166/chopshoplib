package com.chopshop166.chopshoplib.commands;

import java.util.function.DoubleSupplier;
import com.chopshop166.chopshoplib.maps.SimpleMechanismMap;
import com.chopshop166.chopshoplib.maps.SimpleMechanismMap.Data;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * A {@link SmartSubsystem} that goes to specific presets.
 */
public abstract class SimplePresetSubsystem<P extends Enum<P> & DoubleSupplier>
        extends PresetSubsystem<P, Data, SimpleMechanismMap> {

    protected SimplePresetSubsystem(final Data data, final SimpleMechanismMap map) {
        super(data, map);
    }

    abstract Command moveToPreset(final P preset);

    abstract Command zero();

    @Override
    public void reset() {
        super.reset();
        getMap().motor.getEncoder().reset();
    }

    @Override
    public void safeState() {
        getMap().motor.stopMotor();
    }
}
