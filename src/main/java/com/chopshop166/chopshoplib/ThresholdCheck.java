package com.chopshop166.chopshoplib;

import java.util.function.BooleanSupplier;

/**
 * Returns true if the given source is true a specified number of times in a
 * row.
 */
public class ThresholdCheck implements BooleanSupplier {
    final int nTimes;
    final BooleanSupplier source;
    int numSuccesses;

    /**
     * Returns true if the given source is true a specified number of times in a
     * row.
     * 
     * @param nTimes The number of times for a match.
     * @param source The operation to check.
     */
    public ThresholdCheck(int nTimes, BooleanSupplier source) {
        this.nTimes = nTimes;
        this.source = source;
        reset();
    }

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
