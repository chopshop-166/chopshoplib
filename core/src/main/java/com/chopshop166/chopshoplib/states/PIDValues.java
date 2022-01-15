package com.chopshop166.chopshoplib.states;

/**
 * Wrapper for PID values.
 * 
 * This structure is immutable - if you need to change a value, make a new one.
 */
public class PIDValues {
    /** Proportional coefficient. */
    public final double p;
    /** Integral coefficient. */
    public final double i;
    /** Derivative coefficient. */
    public final double d;
    /** Feed-forward coefficient. */
    public final double ff;

    /**
     * Constructor.
     * 
     * @param p Proportional coefficient.
     * @param i Integral coefficient.
     * @param d Derivative coefficient.
     */
    public PIDValues(final double p, final double i, final double d) {
        this(p, i, d, 0.0);
    }

    /**
     * Constructor.
     * 
     * @param p  Proportional coefficient.
     * @param i  Integral coefficient.
     * @param d  Derivative coefficient.
     * @param ff Feed-forward coefficient.
     */
    public PIDValues(final double p, final double i, final double d, final double ff) {
        this.p = p;
        this.i = i;
        this.d = d;
        this.ff = ff;
    }

}
