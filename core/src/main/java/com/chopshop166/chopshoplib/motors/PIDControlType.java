package com.chopshop166.chopshoplib.motors;

/** Control type abstraction. */
public enum PIDControlType {
    /** Velocity based PID. */
    Velocity,
    /** Position based PID. */
    Position,
    /** Voltage */
    Voltage,
    /** Smart Motion. */
    SmartMotion
}
