package com.chopshop166.chopshoplib.sensors;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Sendable;

/**
 * A {@link DigitalInput} that acts as a {@link BooleanSupplier}.
 */
public interface DigitalInputSource extends Sendable, BooleanSupplier {

}