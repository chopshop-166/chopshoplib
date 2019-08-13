package com.chopshop166.chopshoplib.states;

public class Claw2 extends StateSubsystem<Direction> {
  public Claw2() {
    super(Direction.NEUTRAL);

    transition(Direction.NEUTRAL, Direction.FORWARD);
    transition(Direction.NEUTRAL, Direction.REVERSE);
    transition(Direction.REVERSE, Direction.FORWARD);
    transition(Direction.FORWARD, Direction.REVERSE);
    onEntry(Direction.FORWARD, () -> {
      // Solenoid forward
    });
    onEntry(Direction.REVERSE, () -> {
      // Solenoid reverse
    });
  }
}