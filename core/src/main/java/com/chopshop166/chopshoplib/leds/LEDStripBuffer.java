package com.chopshop166.chopshoplib.leds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

/** LED Buffer that creates and stores segments. */
public class LEDStripBuffer {
    /** The list of segments. */
    private final List<SegmentConfig> segmentConfigs = new ArrayList<>();
    /** The mapping of tags to segments. */
    private final Map<String, Set<SegmentConfig>> segmentTagMap = new HashMap<>();
    /** The map of segment to pattern. */
    private final Map<SegmentConfig, Pattern> patternMap = new HashMap<>();
    /** The order of patterns to run on segments. */
    private final List<RunOrder> runOrder = new ArrayList<>();
    /** The patterns that have been added since the last run. */
    private final Set<Pattern> newPatterns = new HashSet<>();
    /** The buffer object. */
    private final AddressableLEDBuffer buffer;
    /** The buffer that gets written */
    private final AddressableLEDBuffer rawBuffer;
    /** Total number of LEDs in segments. */
    private final int numLEDs;
    /** The next LED to assign with factories. */
    private int nextLED;


    /**
     * Constructor.
     * 
     * @param numLEDs Number of LEDs.
     */
    public LEDStripBuffer(final int numLEDs) {
        this.numLEDs = numLEDs;
        this.nextLED = 0;
        this.buffer = new AddressableLEDBuffer(numLEDs);
        this.rawBuffer = new AddressableLEDBuffer(numLEDs);
    }

    /**
     * Get the length of the buffer.
     * 
     * @return The number of LEDs.
     */
    public int getLength() {
        return this.numLEDs;
    }

    /**
     * Get the combined length of all segments with a given tag.
     * 
     * @param tag The tag to search for.
     * @return The total length.
     */
    public int getLengthOfTag(final String tag) {
        return this.segmentConfigs.stream().filter(c -> c.tagSet.contains(tag))
                .mapToInt(c -> c.length).sum();
    }

    /**
     * Factory to create a new segment.
     * 
     * @param length The length of the segment (in LEDs).
     * @return A segment object.
     */
    public SegmentConfig segment(final int length) {
        assert this.numLEDs >= this.nextLED + length;
        assert length > 0;
        final var result = new SegmentConfig(this, this.nextLED, length);
        this.segmentConfigs.add(result);
        this.nextLED += length;
        return result;
    }

    /**
     * Factory to create a new segment.
     * 
     * @param length The length of the segment (in LEDs).
     * @param format The wire format of the LEDs.
     * @return A segment object.
     */
    public SegmentConfig segment(final int length, final ColorFormat format) {
        final var result = this.segment(length);
        result.format = format;
        return result;
    }

    /**
     * Create a segment that mirrors another segment.
     * 
     * @param origConfig The original config to mirror.
     * @return A segment object.
     */
    public SegmentConfig mirrorSegment(final SegmentConfig origConfig) {
        final var result = this.segment(origConfig.length);
        origConfig.mirrors.add(result);
        return result;
    }

    /**
     * Create a segment that mirrors another segment.
     * 
     * @param origConfig The original config to mirror.
     * @param format The wire format of the LEDs.
     * @return A segment object.
     */
    public SegmentConfig mirrorSegment(final SegmentConfig origConfig, final ColorFormat format) {
        final var result = this.segment(origConfig.length, format);
        origConfig.mirrors.add(result);
        return result;
    }

    /**
     * Add tags to a segment.
     * 
     * @param config The segment.
     * @param tags The string tags.
     */
    public void addTags(final SegmentConfig config, final String... tags) {
        for (final String tag : tags) {
            this.segmentTagMap.putIfAbsent(tag, new HashSet<>());
            this.segmentTagMap.get(tag).add(config);
        }
    }

    /**
     * Set a pattern to the entire buffer.
     * 
     * @param pattern The pattern to use.
     */
    public void setGlobalPattern(final Pattern pattern) {
        for (final var config : this.segmentConfigs) {
            this.patternMap.put(config, pattern);
        }
        // Then it triggers a recalculation of run order
        this.recalculateRunOrder();
        this.newPatterns.add(pattern);
    }

    /**
     * Set the pattern for a given tag.
     * 
     * @param tag The tag to assign a pattern to.
     * @param pattern The pattern to use.
     */
    public void setPattern(final String tag, final Pattern pattern) {
        // When setPattern is called, it removes the existing pattern from any segments
        // that match those tags
        // and then uses the given pattern in their place
        //
        // Internally, the scheduler has a Map<String, List<SegmentConfig>>
        // When setPattern is called, it indexes into that map and replaces each data's
        // active pattern with this new one
        for (final var config : this.segmentTagMap.getOrDefault(tag, new HashSet<>())) {
            this.patternMap.put(config, pattern);
        }
        // Then it triggers a recalculation of run order
        this.recalculateRunOrder();
        this.newPatterns.add(pattern);
    }

    /**
     * Update an LED strip with the latest values.
     * 
     * @param led The wpilib LED strip object.
     */
    public void update(final AddressableLED led) {
        for (final var order : this.runOrder) {
            if (this.newPatterns.contains(order.pattern())) {
                order.initialize();
            }
        }
        this.newPatterns.clear();
        // When leds.update() is called, it goes into the run order and calls the
        // pattern's update with the segment buffer
        for (final var order : this.runOrder) {
            order.update();
        }
        for (int i = 0; i < this.numLEDs; i++) {
            final Color c = this.buffer.getLED(i);
            this.rawBuffer.setLED(i, c);
        }
        led.setData(this.rawBuffer);
    }

    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops"})
    private void recalculateRunOrder() {
        // Run order is a List<RunOrder>
        // When calculated, it groups together every segment with the same pattern, in
        // creation order
        final Set<Pattern> matchedPatterns = new HashSet<>();
        this.runOrder.clear();
        for (int i = 0; i < this.segmentConfigs.size(); i++) {
            final Pattern p = this.patternMap.get(this.segmentConfigs.get(i));
            if (p == null) {
                continue;
            }
            // If we haven't checked this pattern already
            if (!matchedPatterns.contains(p)) {
                // Group together all segments with the same pattern
                final List<SegmentConfig> patternConfigs = new ArrayList<>();
                for (int j = i; j < this.segmentConfigs.size(); j++) {
                    final var conf = this.segmentConfigs.get(j);
                    final Pattern p2 = this.patternMap.get(conf);
                    if (p.equals(p2)) {
                        patternConfigs.add(conf);
                    }
                }
                // Create a Segment Buffer from the list
                final var segbuf = new SegmentBuffer(this.buffer, patternConfigs);
                this.runOrder.add(new RunOrder(p, segbuf));
            }
            matchedPatterns.add(p);
        }
    }
}
