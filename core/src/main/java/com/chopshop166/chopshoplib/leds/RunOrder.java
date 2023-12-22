package com.chopshop166.chopshoplib.leds;

/** A single item in a pattern run order. */
/* package */ record RunOrder(Pattern pattern, SegmentBuffer buffer) {

    /** Wrapper to call initialize. */
    public void initialize() {
        this.pattern.initialize(this.buffer);
    }

    /** Wrapper to call update. */
    public void update() {
        this.pattern.update(this.buffer);
    }
}
