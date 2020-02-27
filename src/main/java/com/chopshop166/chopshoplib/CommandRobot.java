package com.chopshop166.chopshoplib;

import java.io.IOException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.google.common.io.Resources;
import com.google.common.reflect.ClassPath;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * A Robot that calls the command scheduler in its periodic functions.
 */
public class CommandRobot extends TimedRobot {

    final private static String UNKNOWN_VALUE = "???";

    @Override
    public void robotInit() {
        super.robotInit();
        logBuildData();
    }

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
        final CommandBase cmd = CommandGroupBase.sequence(first);
        cmd.setName(name);
        return cmd;
    }

    public static CommandBase parallel(final String name, final Command... cmds) {
        final CommandBase cmd = CommandGroupBase.parallel(cmds);
        cmd.setName(name);
        return cmd;
    }

    public static CommandBase race(final String name, final Command racers) {
        final CommandBase cmd = CommandGroupBase.race(racers);
        cmd.setName(name);
        return cmd;
    }

    public static CommandBase deadline(final String name, final Command limiter, final Command... cmds) {
        final CommandBase cmd = CommandGroupBase.deadline(limiter, cmds);
        cmd.setName(name);
        return cmd;
    }

    /**
     * Get a RobotMap for the given name.
     * 
     * @param <T>       The type to return.
     * @param rootClass The root class object that the map derives from.
     * @param pkg       The package to look in.
     * @return An instance of the given type, or null.
     */
    public static <T> T getRobotMap(final Class<T> rootClass, final String pkg) {
        return getMapForName(RobotUtils.getMACAddress(), rootClass, pkg, null);
    }

    /**
     * Get a RobotMap for the given name.
     * 
     * @param <T>          The type to return.
     * @param rootClass    The root class object that the map derives from.
     * @param pkg          The package to look in.
     * @param defaultValue The object to return if no match is found.
     * @return An instance of the given type, or the default value.
     */
    public static <T> T getRobotMap(final Class<T> rootClass, final String pkg, final T defaultValue) {
        return getMapForName(RobotUtils.getMACAddress(), rootClass, pkg, defaultValue);
    }

    /**
     * Get a RobotMap for the given name.
     * 
     * @param <T>       The type to return.
     * @param name      The name to match against in annotations.
     * @param rootClass The root class object that the map derives from.
     * @param pkg       The package to look in.
     * @return An instance of the given type, or null.
     */
    public static <T> T getMapForName(final String name, final Class<T> rootClass, final String pkg) {
        return getMapForName(name, rootClass, pkg, null);
    }

    /**
     * Get a RobotMap for the given name.
     * 
     * @param <T>          The type to return.
     * @param name         The name to match against in annotations.
     * @param rootClass    The root class object that the map derives from.
     * @param pkg          The package to look in.
     * @param defaultValue The object to return if no match is found.
     * @return An instance of the given type, or the default value.
     */
    public static <T> T getMapForName(final String name, final Class<T> rootClass, final String pkg,
            final T defaultValue) {
        try {
            // scans the class path used by classloader
            final ClassPath classpath = ClassPath.from(rootClass.getClassLoader());
            // Get class info for all classes
            for (final ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive(pkg)) {
                final Class<?> clazz = classInfo.load();
                // Make sure the class is derived from rootClass
                if (rootClass.isAssignableFrom(clazz)) {
                    // Cast the class to the derived type
                    final Class<? extends T> theClass = clazz.asSubclass(rootClass);
                    // Find all annotations that provide a name
                    for (final RobotMapFor annotation : clazz.getAnnotationsByType(RobotMapFor.class)) {
                        // Check to see if the name matches
                        if (annotation.value().equals(name)) {
                            return theClass.getDeclaredConstructor().newInstance();
                        }
                    }
                }
            }
        } catch (IOException | ReflectiveOperationException err) {
            return defaultValue;
        }
        return defaultValue;
    }

    /**
     * Put robot code build information onto the dashboard.
     * <p>
     * This will fail without a Gradle task to generate build information. See this
     * ChopShopLib README for more information.
     */
    @SuppressWarnings("PMD.EmptyCatchBlock")
    public static void logBuildData() {

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
