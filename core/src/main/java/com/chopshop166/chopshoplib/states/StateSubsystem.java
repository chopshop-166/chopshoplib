package com.chopshop166.chopshoplib.states;

import static com.chopshop166.chopshoplib.commands.CommandUtils.setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * A {@link Subsystem} that represents a state machine.
 * 
 * @param <S> The enum of possible states.
 */
public abstract class StateSubsystem<S extends Enum<S>> extends SmartSubsystemBase {
    /** The current subsystem state. */
    private S currentState;
    /** The valid transitions. */
    private final Set<Transition<S>> transitions = new HashSet<>();
    /** The handlers when entering a state. */
    private final Map<S, Runnable> onEntryHandlers = new HashMap<>();
    /** The handlers when exiting a state. */
    private final Map<S, Runnable> onExitHandlers = new HashMap<>();
    /** The handlers when inside a state. */
    private final Map<S, Runnable> handlers = new HashMap<>();
    /** Allow all transitions to the same state. */
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
     * @param initState The state that the subsystem starts in.
     * @param allowSameTransition Whether to allow transitions to the current state.
     */
    protected StateSubsystem(final S initState, final boolean allowSameTransition) {
        super();
        this.currentState = initState;
        this.allowSameTransition = allowSameTransition;

        this.setDefaultCommand(new InstantCommand(() -> this.handleState(this.currentState), this));
    }

    /**
     * Set the state of the subsystem, obeying transition rules.
     * 
     * @param newState The new state to transition to.
     */
    public void setState(final S newState) {
        final Transition<S> transition = new Transition<>(this.currentState, newState);
        if (this.transitions.contains(transition)
                || this.allowSameTransition && this.currentState.equals(newState)) {
            Optional.of(this.onExitHandlers.get(this.currentState)).ifPresent(Runnable::run);
            this.currentState = newState;
            Optional.of(this.onEntryHandlers.get(this.currentState)).ifPresent(Runnable::run);
        } else {
            this.defaultTransition(this.currentState, newState);
        }
    }

    /**
     * Get the state of the subsystem.
     * 
     * @return The current state.
     */
    public S getState() {
        return this.currentState;
    }

    /**
     * Create a {@link Command} to change state when run.
     * 
     * @param newState The state to transition to.
     * @return A command that will change the subsystem state.
     */
    public Command changeState(final S newState) {
        final StringBuilder cmdname = new StringBuilder(this.getName());
        cmdname.append(" -> ").append(newState.name());
        return setter(newState, this::setState).withName(cmdname.toString());
    }

    /**
     * Perform an action if an invalid transition is commanded.
     * 
     * Override this to provide a custom implementation.
     * 
     * @param current The current state.
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
     * @param currentState The current state.
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
     * @param current The state to transition from.
     * @param commanded The state to transition to.
     */
    protected final void transition(final S current, final S commanded) {
        this.transitions.add(new Transition<S>(current, commanded));
    }

    /**
     * Register a callback for when the subsystem transitions to the given state.
     * 
     * @param newState The state to transition to.
     * @param action The action to run when transitioning to the state.
     */
    protected final void onEntry(final S newState, final Runnable action) {
        this.onEntryHandlers.put(newState, action);
    }

    /**
     * Register a callback for when the subsystem transitions from the given state.
     * 
     * @param newState The state to transition to.
     * @param action The action to run when transitioning from the state.
     */
    protected final void onExit(final S newState, final Runnable action) {
        this.onExitHandlers.put(newState, action);
    }

    /**
     * Perform an action every iteration that the subsystem is in this state.
     * 
     * Provide a new state to transition to.
     * 
     * @param state The state to run the action in.
     * @param action The action to take in the given state.
     */
    protected final void handle(final S state, final Supplier<S> action) {
        this.handlers.put(state, () -> {
            final S newState = action.get();
            if (!this.getState().equals(newState) || this.allowSameTransition) {
                this.setState(newState);
            }
        });
    }

    /**
     * Perform an action every iteration that the subsystem is in this state.
     * 
     * @param state The state to run the action in.
     * @param action The action to take in the given state.
     */
    protected final void handle(final S state, final Runnable action) {
        this.handlers.put(state, action);
    }

    /**
     * Callback to perform a state's action.
     * 
     * @param state The state to check.
     */
    protected final void handleState(final S state) {
        if (this.handlers.containsKey(state)) {
            this.handlers.get(state).run();
        }
    }

    /**
     * A transition is a pair from one state to another.
     * 
     * @param <State> The state enum type.
     */
    private static final class Transition<State> {
        /** The state to transition from. */
        private final State startState;
        /** The state to transition to. */
        private final State endState;

        /**
         * Create a transition.
         * 
         * @param startState The state to transition from.
         * @param endState The state to transition to.
         */
        public Transition(final State startState, final State endState) {
            this.startState = startState;
            this.endState = endState;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.startState, this.endState);
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
            return t.startState.equals(this.startState) && t.endState.equals(this.endState);
        }
    }
}
