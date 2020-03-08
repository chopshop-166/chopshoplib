package com.chopshop166.chopshoplib;

import java.util.function.BooleanSupplier;

/**
 * Returns true if the given source is true a specified number of times in a
 * row.
 */
public class ThresholdCheck implements BooleanSupplier {

    /** The number of times required to pass. */
    private final int nTimes;
    /** The condition to check. */
    private final BooleanSupplier source;
    /** The number of successes so far. */
    private int numSuccesses;

    /**
     * Returns true if the given source is true a specified number of times in a
     * row.
     * 
     * @param nTimes The number of times for a match.
     * @param source The operation to check.
     */
    public ThresholdCheck(final int nTimes, final BooleanSupplier source) {
        this.nTimes = nTimes;
        this.source = source;
        numSuccesses = 0;
    }

    /** Reset the number of passed attempts. */
    public void reset() {
        numSuccesses = 0;
    }

    @Override
    public boolean getAsBoolean() {
        if (source.getAsBoolean()) {
            numSuccesses++;
        } else {
            reset();
        }
        return numSuccesses >= nTimes;
    }
}
