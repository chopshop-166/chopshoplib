package com.chopshop166.chopshoplib;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.DoubleStream;

/**
 * Implements a circular sample buffer.
 */
public class SampleBuffer implements Iterable<Double> {
    private final double samples[];
    private int sampleIndex;
    private boolean isReset;

    public SampleBuffer() {
        this(25);
    }

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