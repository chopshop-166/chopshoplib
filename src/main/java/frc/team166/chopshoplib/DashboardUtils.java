package frc.team166.chopshoplib;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardUtils {
    private DashboardUtils() {
    }

    public static void initialize(final RobotBase robot) {
        final Class<? extends RobotBase> clazz = robot.getClass();

        for (final Field field : clazz.getDeclaredFields()) {
            // Make the field accessible, because apparently we're allowed to do that
            field.setAccessible(true);
            try {
                // See if the returned object implements sendable.
                // If it does then lets add it as a child.
                if (Subsystem.class.isAssignableFrom(field.getType())) {
                    final Subsystem subsystem = (Subsystem) field.get(robot);
                    initialize(subsystem);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for (final Method method : clazz.getDeclaredMethods()) {
            try {
                for (final Display annotation : method.getAnnotationsByType(Display.class)) {
                    final Double[] args = RobotUtils.toBoxed(annotation.value());
                    final Command command = (Command) method.invoke(robot, (Object[]) args);
                    if (command != null) {
                        String name = annotation.name();
                        if (name.isEmpty()) {
                            name = command.getName();
                        }
                        SmartDashboard.putData(name, command);
                    }
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void initialize(final Subsystem system) {
        final Class<? extends Subsystem> clazz = system.getClass();

        for (final Field field : clazz.getDeclaredFields()) {
            // Make the field accessible, because apparently we're allowed to do that
            field.setAccessible(true);
            try {
                // See if the returned object implements sendable.
                // If it does then lets add it as a child.
                if (Sendable.class.isAssignableFrom(field.getType())) {
                    final Sendable sendable = (Sendable) field.get(system);
                    sendable.setSubsystem(system.getName());
                    system.addChild(field.getName(), sendable);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for (final Method method : clazz.getDeclaredMethods()) {
            try {
                for (final Display annotation : method.getAnnotationsByType(Display.class)) {
                    final Double[] args = RobotUtils.toBoxed(annotation.value());
                    final Command command = (Command) method.invoke(system, (Object[]) args);
                    if (command != null) {
                        String name = annotation.name();
                        if (name.isEmpty()) {
                            name = command.getName();
                        }
                        SmartDashboard.putData(name, command);
                    }
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}