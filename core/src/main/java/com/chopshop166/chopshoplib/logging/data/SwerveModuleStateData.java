package com.chopshop166.chopshoplib.logging.data;

import org.littletonrobotics.junction.LogTable;
import com.chopshop166.chopshoplib.logging.DataWrapper;
import com.chopshop166.chopshoplib.logging.LoggerDataFor;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

/** Data object for a swerve module state. */
@LoggerDataFor(SwerveModuleState.class)
public class SwerveModuleStateData extends DataWrapper {
    /** The WPIlib data object. */
    public SwerveModuleState data = new SwerveModuleState();

    /**
     * Constructor.
     * 
     * @param name The name of the module data.
     */
    public SwerveModuleStateData(final String name) {
        super(name);
    }

    @Override
    public void toLog(final LogTable table) {
        final LogTable subTable = this.getTableFrom(table);
        subTable.put("VelocityMetersPerSec", this.data.speedMetersPerSecond);
        subTable.put("AngleDegrees", this.data.angle.getDegrees());
    }

    @Override
    public void fromLog(final LogTable table) {
        final LogTable subTable = this.getTableFrom(table);
        this.data.speedMetersPerSecond =
                subTable.get("VelocityMetersPerSec", this.data.speedMetersPerSecond);
        this.data.angle =
                Rotation2d.fromDegrees(subTable.get("AngleDegrees", this.data.angle.getDegrees()));
    }
}
