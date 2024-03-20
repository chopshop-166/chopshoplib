package com.chopshop166.chopshoplib.leds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

/** A single LED segment. */
public class SegmentConfig {
    /** The length of the segment */
    /* package */ int length;
    /** The tags assigned to this segment. */
    /* package */ final Set<String> tagSet = new HashSet<>();
    /** Whether the segment is reversed. */
    /* package */ boolean isReversed;
    /** The starting index into the main buffer. */
    /* package */ int startPos;
    /** The ending index for the main buffer. */
    /* package */ int stopPos;
    // Note that the originals store references to their mirrors to make it easier
    // to update
    /**
     * The list of segments that mirror this one
     * 
     * This makes it easier to update.
     */
    /* package */ final List<SegmentConfig> mirrors = new ArrayList<>();
    /** The color format of the segment */
    /* package */ ColorFormat format = ColorFormat.RGB;
    /** The buffer to write to. */
    private final LEDStripBuffer buffer;

    /**
     * Constructor.
     * 
     * @param buffer The buffer to use.
     * @param startPos The starting position in the buffer.
     * @param length The length of the segment.
     */
    /* package */ SegmentConfig(final LEDStripBuffer buffer, final int startPos, final int length) {
        this.buffer = buffer;
        this.startPos = startPos;
        this.stopPos = startPos + length - 1;
        this.length = length;
    }

    /**
     * Set whether a segment is reversed.
     * 
     * @param isReversed Whether the segment is reversed.
     * @return This, for function chaining.
     */
    public SegmentConfig reversed(final boolean isReversed) {
        this.isReversed = isReversed;
        return this;
    }

    /**
     * Assign tags to the segment.
     * 
     * @param newTags The tags (strings) to add.
     * @return This, for function chaining.
     */
    public SegmentConfig tags(final String... newTags) {
        this.tagSet.addAll(Arrays.asList(newTags));
        this.buffer.addTags(this, newTags);
        return this;
    }

    /**
     * Update a buffer with the given color at the right index.
     * 
     * @param i The index within the segment.
     * @param c The color to set.
     * @param buf The buffer to write to.
     */
    public void update(final int i, final Color c, final AddressableLEDBuffer buf) {
        buf.setLED(this.indexFor(i), this.format.convert(c));
        for (final var mirror : this.mirrors) {
            mirror.update(i, c, buf);
        }
    }

    private int indexFor(final int i) {
        if (this.isReversed) {
            return this.stopPos - i;
        } else {
            return this.startPos + i;
        }
    }

    @Override
    public String toString() {
        return String.format("SegmentConfig(%s, %s)", this.startPos, this.stopPos);
    }

}
