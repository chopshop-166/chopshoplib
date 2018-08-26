package frc.team166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class MockSpeedController implements SendableSpeedController {

    private double speed;
    private boolean isInverted;
    private String name;
    private String subsystem;

    @Override
    public void pidWrite(final double output) {
        set(output);
    }

    @Override
    public void set(final double speed) {
        this.speed = isInverted ? -speed : speed;
    }

    @Override
    public double get() {
        return speed;
    }

    @Override
    public void setInverted(final boolean isInverted) {
        this.isInverted = isInverted;
    }

    @Override
    public boolean getInverted() {
        return isInverted;
    }

    @Override
    public void disable() {
        this.speed = 0.0;
    }

    @Override
    public void stopMotor() {
        this.speed = 0.0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getSubsystem() {
        return subsystem;
    }

    @Override
    public void setSubsystem(final String subsystem) {
        this.subsystem = subsystem;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Speed Controller");
        builder.setSafeState(this::disable);
        builder.addDoubleProperty("Value", this::get, this::set);
    }
}
