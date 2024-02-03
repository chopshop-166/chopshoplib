package com.chopshop166.chopshoplib.motors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

/**
 * Smart motor controller that contains a group.
 */
public class SmartMotorControllerGroup extends SmartMotorController {

    /** The wrapped motor controller. */
    private final List<SmartMotorController> wrapped = new ArrayList<>();

    /**
     * Construct for a group
     *
     * @param controller1 The first controller.
     * @param controller2 The second controller.
     * @param controllers Subsequent controllers.
     */
    public SmartMotorControllerGroup(final SmartMotorController controller1,
            final SmartMotorController controller2, final SmartMotorController... controllers) {
        super(controller1, controller1.getEncoder());
        this.wrapped.add(controller1);
        this.wrapped.add(controller2);
        this.wrapped.addAll(Arrays.asList(controllers));

    }

    @Override
    public double[] getCurrentAmps() {
        return this.wrapped.stream().flatMapToDouble(m -> DoubleStream.of(m.getCurrentAmps()))
                .toArray();
    }

    @Override
    public double[] getTemperatureC() {
        return this.wrapped.stream().flatMapToDouble(m -> DoubleStream.of(m.getTemperatureC()))
                .toArray();
    }

    @Override
    public double[] getVoltage() {
        return this.wrapped.stream().flatMapToDouble(m -> DoubleStream.of(m.getVoltage()))
                .toArray();
    }

}
