package com.chopshop166.chopshoplib.outputs;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

/** Convenience alias for a Talon SRX */
public class PIDTalonFX extends PIDTalonBase<WPI_TalonFX> {

    /**
     * Constructor.
     * 
     * @param talon The Talon object to wrap.
     */
    public PIDTalonFX(final WPI_TalonFX talon) {
        super(talon);
    }

}
