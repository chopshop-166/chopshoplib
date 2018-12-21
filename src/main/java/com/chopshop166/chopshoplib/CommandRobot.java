package com.chopshop166.chopshoplib;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class CommandRobot extends TimedRobot {

    @Override
    public void robotPeriodic() {
        // Do not call the super method, remove the annoying print
    }

    @Override
    public void disabledInit() {
        RobotUtils.resetAll(this);
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {
        // Do nothing special.
    }

}
