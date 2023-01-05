package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;

/**
 * An instance of {@link Encoder} that adheres to {@link IEncoder}.
 * 
 * The constructor arguments are the same as the ones in the base class.
 */
public class CSEncoder extends Encoder implements IEncoder {

    /**
     * Create the encoder.
     * 
     * @param channelA         The first channel.
     * @param channelB         The second channel.
     * @param reverseDirection True if the encoder is reversed.
     */
    public CSEncoder(final int channelA, final int channelB, final boolean reverseDirection) {
        super(channelA, channelB, reverseDirection);
    }

    /**
     * Create the encoder.
     * 
     * @param channelA The first channel.
     * @param channelB The second channel.
     */
    public CSEncoder(final int channelA, final int channelB) {
        super(channelA, channelB, false);
    }

    /**
     * Create the encoder.
     * 
     * @param channelA         The first channel.
     * @param channelB         The second channel.
     * @param reverseDirection True if the encoder is reversed.
     * @param encodingType     The encoding type.
     */
    public CSEncoder(final int channelA, final int channelB, final boolean reverseDirection,
            final EncodingType encodingType) {
        super(channelA, channelB, reverseDirection, encodingType);
    }

    /**
     * Create the encoder.
     * 
     * @param channelA         The first channel.
     * @param channelB         The second channel.
     * @param indexChannel     The index channel.
     * @param reverseDirection True if the encoder is reversed.
     */
    public CSEncoder(final int channelA, final int channelB, final int indexChannel, final boolean reverseDirection) {
        super(channelA, channelB, indexChannel, reverseDirection);
    }

    /**
     * Create the encoder.
     * 
     * @param channelA     The first channel.
     * @param channelB     The second channel.
     * @param indexChannel The index channel.
     */
    public CSEncoder(final int channelA, final int channelB, final int indexChannel) {
        super(channelA, channelB, indexChannel);
    }

    /**
     * Create the encoder.
     * 
     * @param sourceA          The first source.
     * @param sourceB          The second source.
     * @param reverseDirection True if the encoder is reversed.
     */
    public CSEncoder(final DigitalSource sourceA, final DigitalSource sourceB, final boolean reverseDirection) {
        super(sourceA, sourceB, reverseDirection);
    }

    /**
     * Create the encoder.
     * 
     * @param sourceA The first source.
     * @param sourceB The second source.
     */
    public CSEncoder(final DigitalSource sourceA, final DigitalSource sourceB) {
        super(sourceA, sourceB);
    }

    /**
     * Create the encoder.
     * 
     * @param sourceA          The first source.
     * @param sourceB          The second source.
     * @param reverseDirection True if the encoder is reversed.
     * @param encodingType     The encoding type.
     */
    public CSEncoder(final DigitalSource sourceA, final DigitalSource sourceB, final boolean reverseDirection,
            final EncodingType encodingType) {
        super(sourceA, sourceB, reverseDirection, encodingType);
    }

    /**
     * Create the encoder.
     * 
     * @param sourceA          The first source.
     * @param sourceB          The second source.
     * @param indexSource      The index source.
     * @param reverseDirection True if the encoder is reversed.
     */
    public CSEncoder(final DigitalSource sourceA, final DigitalSource sourceB, final DigitalSource indexSource,
            final boolean reverseDirection) {
        super(sourceA, sourceB, indexSource, reverseDirection);
    }

    /**
     * Create the encoder.
     * 
     * @param sourceA     The first source.
     * @param sourceB     The second source.
     * @param indexSource The index source.
     */
    public CSEncoder(final DigitalSource sourceA, final DigitalSource sourceB, final DigitalSource indexSource) {
        super(sourceA, sourceB, indexSource);
    }

    @Override
    public boolean isMovingForward() {
        return this.getDirection();
    }
}