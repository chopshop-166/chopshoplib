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
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

    /**
     * Automatically add sendable members to the dashboard.
     * 
     * This sends all sendables, as well as all functions that are decorated with
     * {@link Display} and that return {@link Command} or its derivatives.
     * 
     * @param system The subsystem to initialize.
     */
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

    /**
     * Put robot code build information onto the dashboard.
     * <p>
     * This will fail without a Gradle task to generate build information. For
     * instance:
     * 
     * <pre>
     * {@code
     * def runCommand = { String... args ->
     *    def stdout = new ByteArrayOutputStream()
     *    exec {
     *        commandLine args
     *        standardOutput = stdout
     *    }
     *    return stdout.toString().trim()
     * }
     *
     * def getGitHash = { -> runCommand "git", "describe", "--always" }
     *
     * def getGitBranch = { -> runCommand "git", "rev-parse", "--abbrev-ref", "HEAD" }
     *
     * def getGitFilesChanged = { -> runCommand("git", "diff", "--name-only", "HEAD") }
     *
     * task versionTxt() {
     *     doLast {
     *         String resourcesDir = "$projectDir/src/main/resources"
     *         def logDirBase = new File(resourcesDir)
     *         logDirBase.mkdirs()
     *         new File("$resourcesDir/branch.txt").text = getGitBranch()
     *         new File("$resourcesDir/commit.txt").text = getGitHash()
     *         new File("$resourcesDir/changes.txt").text = getGitFilesChanged()
     *         new File("$resourcesDir/buildtime.txt").text =
     *                  new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())
     *     }
     * }
     *
     * compileJava.dependsOn versionTxt
     *
     * }
     * </pre>
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
        try (InputStream stream = RobotUtils.class.getResourceAsStream("/" + path);
                InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(reader)) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            return "";
        }
    }
}