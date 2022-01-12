package com.chopshop166.chopshoplib.motors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A function from double to double, converting and limiting speeds.
 */
public class ModifierGroup {

    /** The actual modifier list. */
    private final List<Modifier> modifiers = new ArrayList<>();

    /**
     * Run all modifiers.
     *
     * As modifiers could have side effects, this is private.
     *
     * @param rawSpeed The base speed to run
     * @return The new speed
     */
    public double run(final double rawSpeed) {
        double speed = rawSpeed;
        for (final Modifier m : modifiers) {
            speed = m.applyAsDouble(speed);
        }
        return speed;
    }

    /**
     * Add modifiers to the list.
     *
     * @param m  First modifier.
     * @param ms Any extra modifiers (optional).
     */
    public void add(final Modifier m, final Modifier... ms) {
        modifiers.add(m);
        modifiers.addAll(Arrays.asList(ms));
    }

    /**
     * Add all modifiers from a collection.
     *
     * @param ms Collection of modifiers.
     */
    public void addAll(final Collection<? extends Modifier> ms) {
        modifiers.addAll(ms);
    }

    /**
     * Clear the list of modifiers.
     */
    public void clear() {
        modifiers.clear();
    }
}