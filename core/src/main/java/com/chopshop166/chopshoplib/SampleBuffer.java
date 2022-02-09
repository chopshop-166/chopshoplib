package com.chopshop166.chopshoplib;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Implements a circular sample buffer.
 */
public class SampleBuffer<E> implements Iterable<E> {

    /** The samples that are tracked. */
    private E[] samples;
    /** The index of the next sample to add. */
    private int sampleIndex;
    /** True if the buffer has been reset. */
    private boolean isReset;
    /** Class that the Sample Buffer array will be constructed with. */
    private Class<E> clas;

    /**
     * Create a Sample Buffer with a default length of 25.
     * 
     * 
     */
    public SampleBuffer() {
        @SuppressWarnings("unchecked")
        final E[] samples = (E[]) Array.newInstance(clas, 25);
        this.samples = samples;
    }

    /**
     * Create a Sample Buffer.
     * 
     * @param numSamples The number of samples to use
     */
    public SampleBuffer(final int numSamples) {
        @SuppressWarnings("unchecked")
        final E[] samples = (E[]) Array.newInstance(clas, numSamples);
        this.samples = samples;
    }

    /**
     * Clear the samples
     */
    public void reset() {
        Arrays.fill(samples, null);
        sampleIndex = 0;
        isReset = true;
    }

    /**
     * Add a new sample to the buffer.
     * 
     * @param sample The value to add.
     */
    public void addSample(E sample) {
        samples[sampleIndex] = sample;
        sampleIndex++;
        if (sampleIndex == samples.length) {
            isReset = false;
            sampleIndex = 0;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return Stream.of(samples).limit(isReset ? sampleIndex : samples.length).iterator();
    }
}