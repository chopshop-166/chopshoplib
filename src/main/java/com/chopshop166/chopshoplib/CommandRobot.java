package com.chopshop166.chopshoplib;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * A Robot that calls the command scheduler in its periodic functions.
 */
public class CommandRobot extends TimedRobot {

    @Override
    public void robotPeriodic() {
        // Do not call the super method, remove the annoying print
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        RobotUtils.resetAll(this);
    }

    public static CommandBase sequence(final String name, final Command... first) {
        CommandBase cmd = CommandGroupBase.sequence(first);
        cmd.setName(name);
        return cmd;
    }

    public static CommandBase parallel(final String name, final Command... cmds) {
        CommandBase cmd = CommandGroupBase.parallel(cmds);
        cmd.setName(name);
        return cmd;
    }

    public static CommandBase race(final String name, final Command racers) {
        CommandBase cmd = CommandGroupBase.race(racers);
        cmd.setName(name);
        return cmd;
    }

    public static CommandBase deadline(final String name, final Command limiter, final Command... cmds) {
        CommandBase cmd = CommandGroupBase.deadline(limiter, cmds);
        cmd.setName(name);
        return cmd;
    }

}
