package com.chopshop166.chopshoplib.maps;

import java.util.function.DoubleSupplier;
import com.chopshop166.chopshoplib.logging.DataWrapper;
import com.chopshop166.chopshoplib.logging.LoggableMap;
import com.chopshop166.chopshoplib.logging.data.MotorControllerData;
import com.chopshop166.chopshoplib.maps.SimpleMechanismMap.Data;
import com.chopshop166.chopshoplib.motors.SmartMotorController;

public class SimpleMechanismMap implements LoggableMap<Data> {

    public SmartMotorController motor;
    public DoubleSupplier sensor;

    public SimpleMechanismMap(final SmartMotorController motor, final DoubleSupplier sensor) {
        this.motor = motor;
        this.sensor = sensor;
    }

    @Override
    public void updateData(Data data) {
        data.motor.updateData(motor);
        data.position = sensor.getAsDouble();
    }

    public static class Data extends DataWrapper {
        public MotorControllerData motor = new MotorControllerData();
        public double position;
    }
}
