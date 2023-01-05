package com.chopshop166.chopshoplib.states;

/** Sample Claw class using states. */
public class Claw2 extends StateSubsystem<LinearDirection> {
  /** Create a Claw and set up state transitions. */
  public Claw2() {
    super(LinearDirection.NEUTRAL);

    this.transition(LinearDirection.NEUTRAL, LinearDirection.FORWARD);
    this.transition(LinearDirection.NEUTRAL, LinearDirection.REVERSE);
    this.transition(LinearDirection.REVERSE, LinearDirection.FORWARD);
    this.transition(LinearDirection.FORWARD, LinearDirection.REVERSE);
    this.onEntry(LinearDirection.FORWARD, () -> {
      // Solenoid forward
    });
    this.onEntry(LinearDirection.REVERSE, () -> {
      // Solenoid reverse
    });
  }

  @Override
  public void safeState() {
    // No-op
  }
}
