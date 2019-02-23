package com.chopshop166.chopshoplib.triggers;

import static java.util.Arrays.stream;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * A {@link Trigger} that triggers if any trigger passed to it is triggered.
 */
public class OrTrigger extends Trigger {

    private final Trigger[] triggers;

    public OrTrigger(final Trigger... triggers) {
        super();
        this.triggers = triggers.clone();
    }

    @Override
    public boolean get() {
        return stream(triggers).map(Trigger::get).reduce(false, (a, b) -> a || b);
    }
}
