package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;

/**
 * An instance of {@link Encoder} that adheres to {@link IEncoder}.
 * 
 * The constructor arguments are the same as the ones in the base class.
 */
public class WEncoder extends Encoder implements IEncoder {

    public WEncoder(final int channelA, final int channelB, final boolean reverseDirection) {
        super(channelA, channelB, reverseDirection);
    }

    public WEncoder(final int channelA, final int channelB) {
        super(channelA, channelB, false);
    }

    public WEncoder(final int channelA, final int channelB, final boolean reverseDirection,
            final EncodingType encodingType) {
        super(channelA, channelB, reverseDirection, encodingType);
    }

    public WEncoder(final int channelA, final int channelB, final int indexChannel, final boolean reverseDirection) {
        super(channelA, channelB, indexChannel, reverseDirection);
    }

    public WEncoder(final int channelA, final int channelB, final int indexChannel) {
        super(channelA, channelB, indexChannel);
    }

    public WEncoder(final DigitalSource sourceA, final DigitalSource sourceB, final boolean reverseDirection) {
        super(sourceA, sourceB, reverseDirection);
    }

    public WEncoder(final DigitalSource sourceA, final DigitalSource sourceB) {
        super(sourceA, sourceB);
    }

    public WEncoder(final DigitalSource sourceA, final DigitalSource sourceB, final boolean reverseDirection,
            final EncodingType encodingType) {
        super(sourceA, sourceB, reverseDirection, encodingType);
    }

    public WEncoder(final DigitalSource sourceA, final DigitalSource sourceB, final DigitalSource indexSource,
            final boolean reverseDirection) {
        super(sourceA, sourceB, indexSource, reverseDirection);
    }

    public WEncoder(final DigitalSource sourceA, final DigitalSource sourceB, final DigitalSource indexSource) {
        super(sourceA, sourceB, indexSource);
    }

    @Override
    public boolean isMovingForward() {
        return getDirection();
    }
}