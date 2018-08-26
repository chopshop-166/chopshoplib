package frc.team166.chopshoplib;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class CommandRobot extends TimedRobot {

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance()
                .run();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance()
                .run();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance()
                .run();
    }

    @Override
    public void testPeriodic() {
        // Do nothing special.
    }

}
