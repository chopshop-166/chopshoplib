package frc.team166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class MockGyro extends SensorBase implements Gyro {

    private double angle;

    @Override
    public double getAngle() {
        return angle;
    }

    public void setAngle(final double angle) {
        this.angle = angle;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Gyro");
        builder.addDoubleProperty("Value", this::getAngle, this::setAngle);
    }

    @Override
    public void calibrate() {
        // Do nothing
    }

    @Override
    public void reset() {
        angle = 0.0;
    }

    @Override
    public double getRate() {
        return 0;
    }
}