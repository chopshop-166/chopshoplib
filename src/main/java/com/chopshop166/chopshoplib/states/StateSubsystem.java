package com.chopshop166.chopshoplib.states;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import com.chopshop166.chopshoplib.commands.SetCommand;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * A {@link Subsystem} that represents a state machine.
 * 
 * @param <S> The enum of possible states.
 */
public abstract class StateSubsystem<S extends Enum<S>> extends SubsystemBase {
    private S currentState;
    private final Set<Transition<S>> transitions = new HashSet<>();
    private final Map<S, Runnable> onEntryHandlers = new HashMap<>();
    private final Map<S, Runnable> onExitHandlers = new HashMap<>();
    private final Map<S, Runnable> handlers = new HashMap<>();
    private final boolean allowSameTransition;

    /**
     * Constructor.
     * 
     * @param initState The state that the subsystem starts in.
     */
    protected StateSubsystem(final S initState) {
        this(initState, false);
    }

    /**
     * Constructor.
     * 
     * @param initState           The state that the subsystem starts in.
     * @param allowSameTransition Whether to allow transitions to the current state.
     */
    protected StateSubsystem(final S initState, final boolean allowSameTransition) {
        super();
        currentState = initState;
        this.allowSameTransition = allowSameTransition;

        setDefaultCommand(new InstantCommand(() -> handleState(currentState), this));
    }

    /**
     * Set the state of the subsystem, obeying transition rules.
     * 
     * @param newState The new state to transition to.
     */
    public void setState(final S newState) {
        final Transition<S> transition = new Transition<>(currentState, newState);
        if (transitions.contains(transition) || allowSameTransition && currentState.equals(newState)) {
            Optional.of(onExitHandlers.get(currentState)).ifPresent(Runnable::run);
            currentState = newState;
            Optional.of(onEntryHandlers.get(currentState)).ifPresent(Runnable::run);
        } else {
            defaultTransition(currentState, newState);
        }
    }

    /**
     * Get the state of the subsystem.
     * 
     * @return The current state.
     */
    public S getState() {
        return currentState;
    }

    /**
     * Create a {@link Command} to change state when run.
     * 
     * @param newState The state to transition to.
     * @return A command that will change the subsystem state.
     */
    public Command changeState(final S newState) {
        final StringBuilder cmdname = new StringBuilder(getName());
        cmdname.append(" -> ").append(newState.name());
        return new SetCommand<>(cmdname.toString(), this, newState, this::setState);
    }

    /**
     * Perform an action if an invalid transition is commanded.
     * 
     * Override this to provide a custom implementation.
     * 
     * @param current   The current state.
     * @param commanded The commanded state.
     */
    protected void defaultTransition(final S current, final S commanded) {
        // Defaults to silently ignoring
    }

    /**
     * Log a message about an invalid transition.
     * 
     * Call this from {@link #defaultTransition(Enum, Enum)} to log.
     * 
     * @param currentState   The current state.
     * @param commandedState The commanded state.
     */
    protected final void logTransition(final S currentState, final S commandedState) {
        final StringBuilder builder = new StringBuilder("Attempted transition from ");
        builder.append(currentState).append(" to ").append(commandedState);
        final Command cmd = new PrintCommand(builder.toString());
        cmd.schedule();
    }

    /**
     * Allow a transition from one state to another.
     * 
     * @param current   The state to transition from.
     * @param commanded The state to transition to.
     */
    protected final void transition(final S current, final S commanded) {
        transitions.add(new Transition<S>(current, commanded));
    }

    /**
     * Register a callback for when the subsystem transitions to the given state.
     * 
     * @param newState The state to transition to.
     * @param action   The action to run when transitioning to the state.
     */
    protected final void onEntry(final S newState, final Runnable action) {
        onEntryHandlers.put(newState, action);
    }

    /**
     * Register a callback for when the subsystem transitions from the given state.
     * 
     * @param newState The state to transition to.
     * @param action   The action to run when transitioning from the state.
     */
    protected final void onExit(final S newState, final Runnable action) {
        onExitHandlers.put(newState, action);
    }

    /**
     * Perform an action every iteration that the subsystem is in this state.
     * 
     * Provide a new state to transition to.
     * 
     * @param state  The state to run the action in.
     * @param action The action to take in the given state.
     */
    protected final void handle(final S state, final Supplier<S> action) {
        handlers.put(state, () -> {
            final S newState = action.get();
            if (!getState().equals(newState) || allowSameTransition) {
                setState(newState);
            }
        });
    }

    /**
     * Perform an action every iteration that the subsystem is in this state.
     * 
     * @param state  The state to run the action in.
     * @param action The action to take in the given state.
     */
    protected final void handle(final S state, final Runnable action) {
        handlers.put(state, action);
    }

    /**
     * Callback to perform a state's action.
     * 
     * @param state The state to check.
     */
    protected final void handleState(final S state) {
        if (handlers.containsKey(state)) {
            handlers.get(state).run();
        }
    }

    /**
     * A transition is a pair from one state to another.
     * 
     * @param <State> The state enum type.
     */
    private static final class Transition<State> {
        private final State startState;
        private final State endState;

        public Transition(final State startState, final State endState) {
            this.startState = startState;
            this.endState = endState;
        }

        @Override
        public int hashCode() {
            return Objects.hash(startState, endState);
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(final Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Transition)) {
                return false;
            }
            final Transition<State> t = (Transition<State>) obj;
            return t.startState.equals(startState) && t.endState.equals(endState);
        }
    }
}
