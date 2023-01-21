package com.chopshop166.chopshoplib.motors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

/**
 * A group of {@link Modifier} objects.
 */
public class ModifierGroup extends ArrayList<DoubleUnaryOperator> implements DoubleUnaryOperator {

    /** Required by ArrayList/Serializable. */
    protected static final long serialVersionUID = 0L;

    /**
     * Create group with modifiers.
     * 
     * @param ms Modifiers to start with.
     */
    public ModifierGroup(final DoubleUnaryOperator... ms) {
        super();
        this.addAll(Arrays.asList(ms));
    }

    /**
     * Run all modifiers.
     *
     * @param rawSpeed The base speed to start from.
     * @return The new speed.
     */
    public double run(final double rawSpeed) {
        double speed = rawSpeed;
        for (final DoubleUnaryOperator m : this) {
            speed = m.applyAsDouble(speed);
        }
        return speed;
    }

    /**
     * Convenience wrapper to use the modifier group as a modifier.
     */
    @Override
    public double applyAsDouble(final double orig) {
        return this.run(orig);
    }

    /**
     * Add modifiers to the list.
     *
     * @param m First modifier.
     * @param ms Any extra modifiers (optional).
     */
    public void add(final Modifier m, final Modifier... ms) {
        this.add(m);
        this.addAll(Arrays.asList(ms));
    }
}
