package com.chopshop166.chopshoplib.pneumatics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * A wrapper for several solenoids that need to work in unison.
 */
public final class SolenoidGroup implements ISolenoid {

    /** All the solenoids that work in this group. */
    private final List<ISolenoid> wrapped = new ArrayList<>();

    /**
     * Construct the group.
     * 
     * @param first The first group member.
     * @param second The second group member.
     * @param others Any subsequent group members.
     */
    public SolenoidGroup(final ISolenoid first, final ISolenoid second, final ISolenoid... others) {
        this.wrapped.add(first);
        this.wrapped.add(second);
        this.wrapped.addAll(Arrays.asList(others));
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        this.wrapped.get(0).initSendable(builder);
    }

    @Override
    @SuppressWarnings({"PMD.AvoidCatchingGenericException", "PMD.EmptyCatchBlock"})
    public void close() throws Exception {
        this.wrapped.forEach(s -> {
            try {
                s.close();
            } catch (Exception e) {
                // Do nothing, we want to close the others
            }
        });
    }

    @Override
    public void set(final boolean value) {
        this.wrapped.forEach(s -> s.set(value));
    }

    @Override
    public boolean get() {
        return this.wrapped.get(0).get();
    }

    @Override
    public boolean isDisabled() {
        return this.wrapped.get(0).isDisabled();
    }

    @Override
    public void setPulseDuration(final double durationSeconds) {
        this.wrapped.forEach(s -> s.setPulseDuration(durationSeconds));
    }

    @Override
    public void startPulse() {
        this.wrapped.forEach(ISolenoid::startPulse);
    }

}
