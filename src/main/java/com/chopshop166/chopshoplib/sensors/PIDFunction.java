package com.chopshop166.chopshoplib.sensors;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Convert a {@link DoubleSupplier} to a {@link PIDSource}.
 */
public class PIDFunction extends SendableBase implements PIDSource {

    private final DoubleSupplier func;
    private PIDSourceType sourceType;

    /**
     * Construct a {@link PIDSource} from a function.
     * 
     * @param func       The function to get values from.
     * @param sourceType The PID source type.
     */
    public PIDFunction(final DoubleSupplier func, final PIDSourceType sourceType) {
        super();
        this.func = func;
        this.sourceType = sourceType;
    }

    /**
     * Construct a {@link PIDSource} from a function.
     * 
     * Assumes displacement for the source type.
     * 
     * @param func The function to get values from.
     */
    public PIDFunction(final DoubleSupplier func) {
        this(func, PIDSourceType.kDisplacement);
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("PIDFunction");
        builder.addDoubleProperty("Value", this::pidGet, null);
    }

    @Override
    public void setPIDSourceType(final PIDSourceType pidSource) {
        sourceType = pidSource;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return sourceType;
    }

    @Override
    public double pidGet() {
        return func.getAsDouble();
    }

}