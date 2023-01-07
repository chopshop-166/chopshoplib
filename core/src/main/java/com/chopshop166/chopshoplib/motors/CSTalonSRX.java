package com.chopshop166.chopshoplib.motors;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/** Convenience alias for a Talon SRX */
public class CSTalonSRX extends CSTalonBase<WPI_TalonSRX> {

    /** List of Configurations that we can switch between. */
    final private List<TalonSRXConfiguration> config = new ArrayList<>(4);

    /**
     * Constructor.
     *
     * @param talon The Talon object to wrap.
     */
    public CSTalonSRX(final WPI_TalonSRX talon) {
        super(talon, 4096);
    }

    /**
     * Convenience constructor.
     *
     * @param deviceNumber The device number to construct with.
     */
    public CSTalonSRX(final int deviceNumber) {
        this(new WPI_TalonSRX(deviceNumber));
    }

    /**
     * Add the default configuration to the list of configurations and set it in the
     * TalonSRX.
     *
     * @param config Configuration to add to the list of stored configs.
     */
    public void addDefaultConfiguration(final TalonSRXConfiguration config) {
        this.config.add(config);
        this.getMotorController().configAllSettings(config);
    }

    /**
     * Add a configuration to the list of configurations we can swap to.
     *
     * @param config Configuration to add to the list of stored configs.
     */
    public void addConfiguration(final TalonSRXConfiguration config) {
        this.config.add(config);
    }

    @Override
    public void setPidSlot(final int slotId) {
        this.getMotorController().configAllSettings(this.config.get(slotId));
    }

}
