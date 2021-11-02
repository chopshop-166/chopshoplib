package com.chopshop166.chopshoplib.states;

/** Sample Claw class using states. */
public class Claw2 extends StateSubsystem<LinearDirection> {
  /** Create a Claw and set up state transitions. */
  public Claw2() {
    super(LinearDirection.NEUTRAL);

    transition(LinearDirection.NEUTRAL, LinearDirection.FORWARD);
    transition(LinearDirection.NEUTRAL, LinearDirection.REVERSE);
    transition(LinearDirection.REVERSE, LinearDirection.FORWARD);
    transition(LinearDirection.FORWARD, LinearDirection.REVERSE);
    onEntry(LinearDirection.FORWARD, () -> {
      // Solenoid forward
    });
    onEntry(LinearDirection.REVERSE, () -> {
      // Solenoid reverse
    });
  }

  @Override
  public void safeState() {
    // No-op
  }
}
