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
     * @param first  The first group member.
     * @param second The second group member.
     * @param others Any subsequent group members.
     */
    public DSolenoidGroup(final IDSolenoid first, final IDSolenoid second, final IDSolenoid... others) {
        wrapped.add(first);
        wrapped.add(second);
        wrapped.addAll(Arrays.asList(others));
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        wrapped.get(0).initSendable(builder);
    }

    @Override
    @SuppressWarnings({ "PMD.AvoidCatchingGenericException", "PMD.EmptyCatchBlock" })
    public void close() throws Exception {
        wrapped.forEach(s -> {
            try {
                s.close();
            } catch (Exception e) {
                // Do nothing, we want to close the others
            }
        });
    }

    @Override
    public void set(final Value value) {
        wrapped.forEach(s -> s.set(value));
    }

    @Override
    public Value get() {
        return wrapped.get(0).get();
    }

    @Override
    public boolean isFwdSolenoidDisabled() {
        return wrapped.get(0).isFwdSolenoidDisabled();
    }

    @Override
    public boolean isRevSolenoidDisabled() {
        return wrapped.get(0).isRevSolenoidDisabled();
    }

}
