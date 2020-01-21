package com.chopshop166.chopshoplib;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.google.common.io.Resources;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Utility class for dashboard-related capabilities.
 */
public final class DashboardUtils {

    final private static String UNKNOWN_VALUE = "???";

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
    @SuppressWarnings("PMD.EmptyCatchBlock")
    public static void logTelemetry() {
        final ShuffleboardTab tab = Shuffleboard.getTab("BuildData");
        String hashString = UNKNOWN_VALUE;
        String buildTime = UNKNOWN_VALUE;
        String branchString = UNKNOWN_VALUE;
        String fileString = UNKNOWN_VALUE;

        try {
            final URL manifestURL = Resources.getResource("META-INF/MANIFEST.MF");
            final Manifest manifest = new Manifest(manifestURL.openStream());
            final Attributes attrs = manifest.getMainAttributes();

            hashString = attrs.getValue("Git-Hash");
            buildTime = attrs.getValue("Build-Time");
            branchString = attrs.getValue("Git-Branch");
            fileString = attrs.getValue("Git-Files");
        } catch (IOException ex) {
            // Could not read the manifest, just send dummy values
        } finally {
            tab.add("Git Hash", hashString).withPosition(0, 0);
            tab.add("Build Time", buildTime).withPosition(1, 0).withSize(2, 1);
            tab.add("Git Branch", branchString).withPosition(3, 0);
            tab.add("Git Files", fileString).withPosition(0, 1).withSize(4, 1);
        }
    }
}