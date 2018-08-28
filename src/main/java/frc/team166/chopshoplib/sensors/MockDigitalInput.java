package frc.team166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class MockDigitalInput extends SendableBase implements DigitalInputSource {

    private boolean value;

    public void setValue(final boolean value) {
        this.value = value;
    }

    @Override
    public boolean getAsBoolean() {
        return value;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("Digital Input");
        builder.addBooleanProperty("Value", this::getAsBoolean, this::setValue);
    }
}
