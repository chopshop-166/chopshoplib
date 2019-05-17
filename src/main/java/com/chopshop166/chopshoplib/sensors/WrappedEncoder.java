package com.chopshop166.chopshoplib.sensors;

import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;

public class WrappedEncoder extends Encoder implements EncoderInterface {

    public WrappedEncoder(final int channelA, final int channelB, final boolean reverseDirection) {
        super(channelA, channelB, reverseDirection);
    }

    public WrappedEncoder(final int channelA, final int channelB) {
        super(channelA, channelB, false);
    }

    public WrappedEncoder(final int channelA, final int channelB, final boolean reverseDirection,
            final EncodingType encodingType) {
        super(channelA, channelB, reverseDirection, encodingType);
    }

    public WrappedEncoder(final int channelA, final int channelB, final int indexChannel,
            final boolean reverseDirection) {
        super(channelA, channelB, indexChannel, reverseDirection);
    }

    public WrappedEncoder(final int channelA, final int channelB, final int indexChannel) {
        super(channelA, channelB, indexChannel);
    }

    public WrappedEncoder(final DigitalSource sourceA, final DigitalSource sourceB, final boolean reverseDirection) {
        super(sourceA, sourceB, reverseDirection);
    }

    public WrappedEncoder(final DigitalSource sourceA, final DigitalSource sourceB) {
        super(sourceA, sourceB);
    }

    public WrappedEncoder(final DigitalSource sourceA, final DigitalSource sourceB, final boolean reverseDirection,
            final EncodingType encodingType) {
        super(sourceA, sourceB, reverseDirection, encodingType);
    }

    public WrappedEncoder(final DigitalSource sourceA, final DigitalSource sourceB, final DigitalSource indexSource,
            final boolean reverseDirection) {
        super(sourceA, sourceB, indexSource, reverseDirection);
    }

    public WrappedEncoder(final DigitalSource sourceA, final DigitalSource sourceB, final DigitalSource indexSource) {
        super(sourceA, sourceB, indexSource);
    }

    @Override
    public boolean isMovingForward() {
        return getDirection();
    }
}