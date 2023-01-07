package com.chopshop166.chopshoplib.states;

/**
 * Wrapper for PID values.
 * 
 * This structure is immutable - if you need to change a value, make a new one.
 * 
 * @param p  Proportional coefficient.
 * @param i  Integral coefficient.
 * @param d  Derivative coefficient.
 * @param ff Feed forward.
 */
public record PIDValues(double p, double i, double d, double ff) {

    /**
     * Constructor with no feed-forward.
     * 
     * @param p Proportional coefficient.
     * @param i Integral coefficient.
     * @param d Derivative coefficient.
     */
    public PIDValues(final double p, final double i, final double d) {
        this(p, i, d, 0.0);
    }

}
