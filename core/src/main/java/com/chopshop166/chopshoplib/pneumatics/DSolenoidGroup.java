package com.chopshop166.chopshoplib.pneumatics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * A wrapper for several double solenoids that need to work in unison.
 */
public final class DSolenoidGroup implements IDSolenoid {

    /** All the solenoids that work in this group. */
    private final List<IDSolenoid> wrapped = new ArrayList<>();

    /**
     * Construct the group.
     * 
     * @param first The first group member.
     * @param second The second group member.
     * @param others Any subsequent group members.
     */
    public DSolenoidGroup(final IDSolenoid first, final IDSolenoid second,
            final IDSolenoid... others) {
        this.wrapped.add(first);
        this.wrapped.add(second);
        this.wrapped.addAll(Arrays.asList(others));
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        this.wrapped.get(0).initSendable(builder);
    }

    @Override
    @SuppressWarnings({"PMD.AvoidCatchingGenericException"})
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
    public void set(final Value value) {
        this.wrapped.forEach(s -> s.set(value));
    }

    @Override
    public Value get() {
        return this.wrapped.get(0).get();
    }

    @Override
    public boolean isFwdSolenoidDisabled() {
        return this.wrapped.get(0).isFwdSolenoidDisabled();
    }

    @Override
    public boolean isRevSolenoidDisabled() {
        return this.wrapped.get(0).isRevSolenoidDisabled();
    }

}
