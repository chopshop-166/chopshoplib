package com.chopshop166.chopshoplib;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.DoubleStream;

/**
 * Implements a circular sample buffer.
 */
public class SampleBuffer implements Iterable<Double> {
    /** The samples that are tracked. */
    private final double samples[];
    /** The index of the next sample to add. */
    private int sampleIndex;
    /** True if the buffer has been reset. */
    private boolean isReset;

    /** Create a Sample Buffer with 25 elements. */
    public SampleBuffer() {
        this(25);
    }

    /**
     * Create a Sample Buffer.
     * 
     * @param numSamples The number of samples to use
     */
    public SampleBuffer(final int numSamples) {
        samples = new double[numSamples];
    }

    /**
     * Clear the samples
     */
    public void reset() {
        synchronized (this) {
            Arrays.fill(samples, 0);
            sampleIndex = 0;
            isReset = true;
        }
    }

    /**
     * Add a new sample to the buffer.
     * 
     * @param sample The value to add.
     */
    public void addSample(final double sample) {
        samples[sampleIndex] = sample;
        sampleIndex++;
        if (sampleIndex == samples.length) {
            isReset = false;
            sampleIndex = 0;
        }
    }

    @Override
    public Iterator<Double> iterator() {
        return DoubleStream.of(samples).limit(isReset ? sampleIndex : samples.length).iterator();
    }
}