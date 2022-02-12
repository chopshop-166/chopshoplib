package com.chopshop166.chopshoplib;

import java.util.LinkedList;

/**
 * Implements a circular sample buffer.
 * 
 * Though it can be used as a linked list for other operations, it's
 * discouraged. Use add to verify that adding an element removes old
 * elements as necessary.
 */
public class SampleBuffer<E> extends LinkedList<E> {

    /** Needed in order to satisfy Java for serialization. */
    public static final long serialVersionUID = 1L;

    /** The index of the next sample to add. */
    private final int sampleCap;

    /**
     * Create a Sample Buffer with a default length of 25.
     */
    public SampleBuffer() {
        this(25);
    }

    /**
     * Create a Sample Buffer.
     * 
     * @param numSamples The number of samples to use
     */
    public SampleBuffer(final int numSamples) {
        super();
        this.sampleCap = numSamples;
    }

    /**
     * Add a new sample to the buffer.
     * 
     * @param sample The value to add.
     */
    @Override
    public boolean add(final E sample) {
        if (size() >= this.sampleCap) {
            removeFirst();
        }
        return super.add(sample);
    }

}