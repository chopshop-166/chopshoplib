package frc.team166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class MockPIDSource extends SendableBase implements PIDSource {

    private double value;
    private PIDSourceType sourceType;

    public void pidSet(final double newValue) {
        value = newValue;
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
        return value;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("MockPIDSource");
        builder.addDoubleProperty("Value", this::pidGet, this::pidSet);
    }
}
