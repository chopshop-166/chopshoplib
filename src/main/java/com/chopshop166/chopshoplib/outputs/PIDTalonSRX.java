package com.chopshop166.chopshoplib.outputs;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/** Convenience alias for a Talon SRX */
public class PIDTalonSRX extends PIDTalonBase<WPI_TalonSRX> {

    /**
     * Constructor.
     * 
     * @param talon The Talon object to wrap.
     */
    public PIDTalonSRX(final WPI_TalonSRX talon) {
        super(talon);
    }

}
