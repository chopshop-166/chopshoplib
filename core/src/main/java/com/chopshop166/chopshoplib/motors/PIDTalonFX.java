package com.chopshop166.chopshoplib.motors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

/** Convenience alias for a Talon SRX */
public class PIDTalonFX extends PIDTalonBase<WPI_TalonFX> {

    /** List of Configurations that we can switch between. */
    final private List<TalonFXConfiguration> config = new ArrayList<>(4);

    /**
     * Constructor.
     *
     * @param talon The Talon object to wrap.
     */
    public PIDTalonFX(final WPI_TalonFX talon) {
        super(talon, 2048);
    }

    /**
     * Convenience constructor.
     *
     * @param deviceNumber The device number to construct with.
     */
    public PIDTalonFX(final int deviceNumber) {
        this(new WPI_TalonFX(deviceNumber));
    }

    /**
     * Add the default configuration to the list of configurations and set it in the
     * TalonFX.
     *
     * @param config Configuration to add to the list of stored configs.
     */
    public void addDefaultConfiguration(final TalonFXConfiguration config) {
        this.config.add(config);
        getMotorController().configAllSettings(config);
    }

    /**
     * Add a configuration to the list of configurations we can swap to.
     *
     * @param config Configuration to add to the list of stored configs.
     */
    public void addConfiguration(final TalonFXConfiguration config) {
        this.config.add(config);
    }

    /**
     * Configure base type parameters inline.
     * 
     * @param conf The configuration function.
     * @return This object.
     */
    public PIDTalonFX configure(final Consumer<WPI_TalonFX> conf) {
        conf.accept(getMotorController());
        return this;
    }

    @Override
    public void setPidSlot(final int slotId) {
        getMotorController().configAllSettings(this.config.get(slotId));
    }
}
