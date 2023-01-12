package com.chopshop166.chopshoplib.motors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

import com.chopshop166.chopshoplib.sensors.IEncoder;
import com.chopshop166.chopshoplib.sensors.MockEncoder;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

/**
 * Smart motor controller that contains a group.
 */
public class SmartMotorControllerGroup extends SmartMotorController {

    /** The wrapped motor controller. */
    private final List<SmartMotorController> wrapped = new ArrayList<>();

    /**
     * Construct for a group.
     * 
     * @param controller1 The first controller.
     * @param controller2 The second controller.
     * @param controllers Subsequent controllers.
     */
    public SmartMotorControllerGroup(final SmartMotorController controller1,
            final SmartMotorController controller2, final SmartMotorController... controllers) {
        this(new MockEncoder(), controller1, controller2, controllers);
    }

    /**
     * Construct for a group with an encoder.
     * 
     * @param encoder The encoder to measure with.
     * @param controller1 The first controller.
     * @param controller2 The second controller.
     * @param controllers Subsequent controllers.
     */
    public SmartMotorControllerGroup(final IEncoder encoder, final SmartMotorController controller1,
            final SmartMotorController controller2, final SmartMotorController... controllers) {
        super(grouped(controller1, controller2, controllers), encoder);
        wrapped.add(controller1);
        wrapped.add(controller2);
        wrapped.addAll(Arrays.asList(controllers));
    }

    @Override
    public double[] getCurrentAmps() {
        return wrapped.stream().flatMapToDouble(m -> DoubleStream.of(m.getCurrentAmps())).toArray();
    }

    @Override
    public double[] getTemperatureC() {
        return wrapped.stream().flatMapToDouble(m -> DoubleStream.of(m.getTemperatureC()))
                .toArray();
    }

    private static MotorControllerGroup grouped(final MotorController mc1,
            final MotorController mc2, final MotorController... mcs) {
        final MotorController[] result = new MotorController[mcs.length + 2];
        result[0] = mc1;
        result[1] = mc2;
        System.arraycopy(mcs, 0, result, 2, mcs.length);
        return new MotorControllerGroup(result);
    }
}
