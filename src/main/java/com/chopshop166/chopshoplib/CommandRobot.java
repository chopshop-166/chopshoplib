package com.chopshop166.chopshoplib;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.frc2.command.CommandScheduler;

/**
 * A Robot that calls the command scheduler in its periodic functions.
 */
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
        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {
        // Do nothing special.
    }

}
