package com.chopshop166.chopshoplib.motors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

/**
 * A group of {@link Modifier} objects.
 */
public class ModifierGroup implements DoubleUnaryOperator {

    /** The actual modifier list. */
    private final List<DoubleUnaryOperator> modifiers = new ArrayList<>();

    /**
     * Create group with modifiers.
     * 
     * @param ms Modifiers to start with.
     */
    public ModifierGroup(final DoubleUnaryOperator... ms) {
        this.modifiers.addAll(Arrays.asList(ms));
    }

    /**
     * Run all modifiers.
     *
     * @param rawSpeed The base speed to start from.
     * @return The new speed.
     */
    public double run(final double rawSpeed) {
        double speed = rawSpeed;
        for (final DoubleUnaryOperator m : this.modifiers) {
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
     * @param m  First modifier.
     * @param ms Any extra modifiers (optional).
     */
    public void add(final Modifier m, final Modifier... ms) {
        this.modifiers.add(m);
        this.modifiers.addAll(Arrays.asList(ms));
    }

    /**
     * Add all modifiers from a collection.
     *
     * @param ms Collection of modifiers.
     */
    public void addAll(final Collection<? extends Modifier> ms) {
        this.modifiers.addAll(ms);
    }

    /**
     * Clear the list of modifiers.
     */
    public void clear() {
        this.modifiers.clear();
    }
}