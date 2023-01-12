package com.chopshop166.chopshoplib.states;

/** Sample Claw class using states. */
public class Claw extends StateSubsystem<OpenClose> {
    /** Create a Claw and set up state transitions. */
    public Claw() {
        super(OpenClose.OPEN);

        this.transition(OpenClose.OPEN, OpenClose.CLOSED);
        this.transition(OpenClose.CLOSED, OpenClose.OPEN);
        this.onEntry(OpenClose.OPEN, () -> {
            // Change solenoid
        });
        this.onEntry(OpenClose.CLOSED, () -> {
            // Change solenoid
        });
        this.handle(OpenClose.OPEN, () -> {
            // If it's in the open state, close it
            return OpenClose.CLOSED;
        });
    }

    @Override
    protected void defaultTransition(final OpenClose currentState, final OpenClose commandedState) {
        this.logTransition(currentState, commandedState);
    }

    @Override
    public void safeState() {
        this.setState(OpenClose.OPEN);
    }
}
