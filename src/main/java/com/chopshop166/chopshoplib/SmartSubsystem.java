package com.chopshop166.chopshoplib;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class SmartSubsystem extends SubsystemBase implements Resettable {

    public FunctionalCommand functional(final String name, final Runnable onInit, final Runnable onExecute,
            final Consumer<Boolean> onEnd, final BooleanSupplier isFinished) {
        final FunctionalCommand cmd = new FunctionalCommand(onInit, onExecute, onEnd, isFinished, this);
        cmd.setName(name);
        return cmd;
    }

    public InstantCommand instant(final String name, final Runnable action) {
        final InstantCommand cmd = new InstantCommand(action, this);
        cmd.setName(name);
        return cmd;
    }

    public RunCommand running(final String name, final Runnable action) {
        final RunCommand cmd = new RunCommand(action, this);
        cmd.setName(name);
        return cmd;
    }

    public StartEndCommand startend(final String name, final Runnable onStart, final Runnable onStop) {
        final StartEndCommand cmd = new StartEndCommand(onStart, onStop, this);
        cmd.setName(name);
        return cmd;
    }

    public CommandBase resetCmd() {
        return instant("Reset " + getName(), this::reset);
    }
}
