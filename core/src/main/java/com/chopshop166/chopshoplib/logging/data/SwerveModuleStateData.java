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

    @Override
    public void toLog(final LogTable table) {
        table.put("VelocityMetersPerSec", this.data.speedMetersPerSecond);
        table.put("AngleDegrees", this.data.angle.getDegrees());
    }

    @Override
    public void fromLog(final LogTable table) {
        this.data.speedMetersPerSecond =
                table.get("VelocityMetersPerSec", this.data.speedMetersPerSecond);
        this.data.angle =
                Rotation2d.fromDegrees(table.get("AngleDegrees", this.data.angle.getDegrees()));
    }
}
