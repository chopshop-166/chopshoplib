package com.chopshop166.chopshoplib.states;

public class Claw extends StateSubsystem<OpenClose> {
  public Claw() {
    super(OpenClose.OPEN);

    transition(OpenClose.OPEN, OpenClose.CLOSED);
    transition(OpenClose.CLOSED, OpenClose.OPEN);
    onEntry(OpenClose.OPEN, () -> {
      // Change solenoid
    });
    onEntry(OpenClose.CLOSED, () -> {
      // Change solenoid
    });
    handle(OpenClose.OPEN, () -> {
      // If it's in the open state, close it
      return OpenClose.CLOSED;
    });
  }

  @Override
  protected void defaultTransition(final OpenClose currentState, final OpenClose commandedState) {
    logTransition(currentState, commandedState);
  }
}
