package com.chopshop166.chopshoplib;

import java.util.LinkedList;

/**
 * Implements a circular sample buffer.
 */
public class SampleBuffer<E> extends LinkedList<E> {

    /** The index of the next sample to add. */
    private int sampleCap;

    /**
     * Create a Sample Buffer with a default length of 25.
     */
    public SampleBuffer() {
        this.sampleCap = 25;
    }

    /**
     * Create a Sample Buffer.
     * 
     * @param numSamples The number of samples to use
     */
    public SampleBuffer(final int numSamples) {
        this.sampleCap = numSamples;
    }

    /**
     * Add a new sample to the buffer.
     * 
     * @param sample The value to add.
     */
    @Override
    public boolean add(E sample) {
        if (size() >= this.sampleCap) {
            removeFirst();
        }
        return super.add(sample);
    }

}