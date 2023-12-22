package com.chopshop166.chopshoplib.leds;

import java.util.List;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

/** A group of segments as one addressable buffer. */
public class SegmentBuffer {

    /** The buffer object to write to. */
    private final AddressableLEDBuffer buf;
    /** The segments that build this buffer. */
    private final List<SegmentConfig> confs;

    /**
     * Constructor.
     * 
     * @param buf The buffer object.
     * @param confs The configs.
     */
    /* package */ SegmentBuffer(final AddressableLEDBuffer buf, final List<SegmentConfig> confs) {
        this.buf = buf;
        this.confs = confs;
    }

    /**
     * Set an LED at a given index, to the given color.
     * 
     * @param index The index to set.
     * @param c The color to set it to.
     */
    public void set(final int index, final Color c) {
        int realIndex = index;
        for (final var conf : this.confs) {
            if (realIndex < conf.length) {
                conf.update(realIndex, c, this.buf);
                return;
            } else {
                realIndex -= conf.length;
            }
        }
    }

    /**
     * Set all segments in the buffer to the given color.
     * 
     * @param c The color to set.
     */
    public void setAll(final Color c) {
        for (final var conf : this.confs) {
            for (int i = 0; i < conf.length; i++) {
                conf.update(i, c, this.buf);
            }
        }
    }

    /**
     * Get the total length of all segment parts.
     * 
     * @return The buffer length.
     */
    public int getLength() {
        return this.confs.stream().mapToInt(c -> c.length).sum();
    }

    @Override
    public String toString() {
        return "SegmentBuffer(" + this.confs.stream().toList().toString() + ")";
    }
}
