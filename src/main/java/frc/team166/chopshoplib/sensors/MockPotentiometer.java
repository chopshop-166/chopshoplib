package frc.team166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class MockPotentiometer extends SensorBase implements Potentiometer {

    private PIDSourceType sourceType;
    private double value;

    public void set(final double value) {
        this.value = value;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Potentiometer");
        builder.addDoubleProperty("Value", this::get, this::set);
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
        return get();
    }

    @Override
    public double get() {
        return value;
    }
}