package com.chopshop166.chopshoplib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Utility class for dashboard-related capabilities.
 */
public final class DashboardUtils {
    private DashboardUtils() {
    }

    /**
     * Automatically add all subsystems to the dashboard.
     * 
     * <p>
     * This exists so it can be called in one place with minimal setup.
     * 
     * @param robot The robot to initialize.
     */
    public static void initialize(final RobotBase robot) {
        final Class<? extends RobotBase> clazz = robot.getClass();

        for (final Field field : clazz.getDeclaredFields()) {
            // Make the field accessible, because apparently we're allowed to do that
            field.setAccessible(true);
            try {
                // See if the returned object implements sendable.
                // If it does then lets add it as a child.
                if (Subsystem.class.isAssignableFrom(field.getType())) {
                    final SubsystemBase subsystem = (SubsystemBase) field.get(robot);
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
                    final CommandBase command = (CommandBase) method.invoke(robot, (Object[]) args);
                    if (command != null) {
                        String name = annotation.name();
                        if (name.isEmpty()) {
                            name = command.getName();
                        }
                        SendableRegistry.add(command, name);
                    }
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Automatically add sendable members to the dashboard.
     * 
     * This sends all sendables, as well as all functions that are decorated with
     * {@link Display} and that return {@link Command} or its derivatives.
     * 
     * @param system The subsystem to initialize.
     */
    public static void initialize(final SubsystemBase system) {
        final Class<? extends Subsystem> clazz = system.getClass();

        for (final Field field : clazz.getDeclaredFields()) {
            // Make the field accessible, because apparently we're allowed to do that
            field.setAccessible(true);
            try {
                // See if the returned object implements sendable.
                // If it does then lets add it as a child.
                if (Sendable.class.isAssignableFrom(field.getType())) {
                    final Sendable sendable = (Sendable) field.get(system);
                    SendableRegistry.addChild(system, sendable);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for (final Method method : clazz.getDeclaredMethods()) {
            try {
                for (final Display annotation : method.getAnnotationsByType(Display.class)) {
                    final Double[] args = RobotUtils.toBoxed(annotation.value());
                    final CommandBase command = (CommandBase) method.invoke(system, (Object[]) args);
                    if (command != null) {
                        String name = annotation.name();
                        if (name.isEmpty()) {
                            name = command.getName();
                        }
                        SendableRegistry.add(command, name);
                    }
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Put robot code build information onto the dashboard.
     * <p>
     * This will fail without a Gradle task to generate build information. See this
     * ChopShopLib README for more information.
     */
    public static void logTelemetry() {
        final String branch = getResource("branch.txt");
        SmartDashboard.putString("branch", branch);

        final String commit = getResource("commit.txt");
        SmartDashboard.putString("commit", commit);

        final String changes = getResource("changes.txt");
        SmartDashboard.putString("changes", changes);

        final String buildtime = getResource("buildtime.txt");
        SmartDashboard.putString("buildtime", buildtime);
    }

    /**
     * Get a resource as a string.
     */
    private static String getResource(final String path) {
        final ClassLoader loader = ClassLoader.getSystemClassLoader();
        final InputStream stream = loader.getResourceAsStream("/" + path);
        if (stream != null) {
            try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                    BufferedReader bufferedReader = new BufferedReader(reader)) {
                return bufferedReader.lines().collect(Collectors.joining("\n"));
            } catch (IOException e) {
                return "";
            }
        }
        return null;
    }
}