package com.chopshop166.chopshoplib;

import edu.wpi.first.wpilibj.TimedRobot;
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

}
