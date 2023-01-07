package com.chopshop166.chopshoplib.commands;

import static edu.wpi.first.wpilibj2.command.Commands.parallel;

import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.net.URL;
import java.util.Arrays;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.stream.Stream;

import com.chopshop166.chopshoplib.Autonomous;
import com.chopshop166.chopshoplib.RobotUtils;
import com.chopshop166.chopshoplib.maps.RobotMapFor;
import com.google.common.io.Resources;
import com.google.common.reflect.ClassPath;

import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * A Robot that calls the command scheduler in its periodic functions.
 * 
 * Contains convenient wrappers for the commands that are often used in groups.
 */
@SuppressWarnings({ "PMD.ExcessiveImports", "PMD.GodClass" })
public abstract class CommandRobot extends TimedRobot {

    /** The value to display on Shuffleboard if Git data isn't found. */
    final private static String UNKNOWN_VALUE = "???";
    /** Chooser for the autonomous mode. */
    final private SendableChooser<Command> autoChooser = new SendableChooser<>();
    /** Currently running autonomous command. */
    private Command autoCmd;

    /** Set up the button bindings. */
    public abstract void configureButtonBindings();

    /** Send commands and data to Shuffleboard. */
    public abstract void populateDashboard();

    /** Set the default commands for each subsystem. */
    public abstract void setDefaultCommands();

    @Override
    public void robotInit() {
        super.robotInit();
        CommandRobot.logBuildData();
        this.configureButtonBindings();
        this.populateDashboard();
        this.setDefaultCommands();

        this.populateAutonomous();
        Shuffleboard.getTab("Shuffleboard").add("Autonomous", this.autoChooser);
    }

    @Override
    public void robotPeriodic() {
        // Do not call the super method, remove the annoying print
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This autonomous runs the autonomous command selected by the chooser.
     */
    @Override
    public void autonomousInit() {
        this.autoCmd = this.getAutoCommand();

        // schedule the autonomous command (example)
        if (this.autoCmd != null) {
            this.autoCmd.schedule();
        }
    }

    @Override
    public void teleopInit() {
        if (this.autoCmd != null) {
            this.autoCmd.cancel();
        }
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * Get the autonomous command to run.
     * 
     * By default, this is from a SendableChooser.
     * 
     * @return A {@link Command} object
     */
    public Command getAutoCommand() {
        return this.autoChooser.getSelected();
    }

    /** Add all the autonomous modes to the chooser. */
    public void populateAutonomous() {
        final Class<? extends CommandRobot> clazz = this.getClass();

        // Get all fields of the class
        Arrays.stream(clazz.getDeclaredFields())
                // Filter for the ones that we can make accessible
                .filter(AccessibleObject::trySetAccessible)
                // Filter for the ones that return a Command-derived type
                .filter(field -> Command.class.isAssignableFrom(field.getType()))
                // Make sure it has the annotation
                .filter(field -> field.getAnnotation(Autonomous.class) != null)
                // Access each field and return a pair of (Command, Field)
                .map(field -> {
                    try {
                        return Pair.of((Command) field.get(this), field.getAnnotation(Autonomous.class));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                // Remove the ones that aren't valid
                .filter(p -> p != null)
                // Add each one to the selector
                .forEach(p -> {
                    final Command cmd = p.getFirst();
                    final Autonomous annotation = p.getSecond();

                    String name = annotation.name();
                    if (name.isEmpty()) {
                        name = cmd.getName();
                    }

                    if (annotation.defaultAuto()) {
                        this.autoChooser.setDefaultOption(name, cmd);
                    } else {
                        this.autoChooser.addOption(name, cmd);
                    }
                });
    }

    /**
     * Create a command that resets the provided objects' sensors.
     * 
     * @param subsystems The subsystems to make safe.
     * @return A command
     */
    public CommandBase resetSubsystems(final SmartSubsystem... subsystems) {
        return parallel(Stream.of(subsystems).map(SmartSubsystem::resetCmd).toArray(CommandBase[]::new))
                .withName("Reset Subsystems");
    }

    /**
     * Create a command that set the provided objects to their safe state.
     * 
     * @param subsystems The subsystems to make safe.
     * @return A command
     */
    public CommandBase safeStateSubsystems(final SmartSubsystem... subsystems) {
        return parallel(Stream.of(subsystems).map(SmartSubsystem::safeStateCmd).toArray(CommandBase[]::new))
                .withName("Reset Subsystems");
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
        return CommandRobot.getRobotMap(rootClass, pkg, null);
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
        return CommandRobot.getMapForName(RobotUtils.getMACAddress(), rootClass, pkg, defaultValue);
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
        return CommandRobot.getMapForName(name, rootClass, pkg, null);
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
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();
            // scans the class path used by classloader
            final ClassPath classpath = ClassPath.from(loader);
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
                        if (annotation.value().equalsIgnoreCase(name)) {
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
        String hashString = CommandRobot.UNKNOWN_VALUE;
        String buildTime = CommandRobot.UNKNOWN_VALUE;
        String branchString = CommandRobot.UNKNOWN_VALUE;
        String fileString = CommandRobot.UNKNOWN_VALUE;
        String macAddress = CommandRobot.UNKNOWN_VALUE;

        try {
            final URL manifestURL = Resources.getResource("META-INF/MANIFEST.MF");
            final Manifest manifest = new Manifest(manifestURL.openStream());
            final Attributes attrs = manifest.getMainAttributes();

            hashString = CommandRobot.getAttr(attrs, "Git-Hash");
            buildTime = CommandRobot.getAttr(attrs, "Build-Time");
            branchString = CommandRobot.getAttr(attrs, "Git-Branch");
            fileString = CommandRobot.getAttr(attrs, "Git-Files");
            macAddress = RobotUtils.getMACAddress();
        } catch (IOException ex) {
            // Could not read the manifest, just send dummy values
        } finally {
            tab.add("Git Hash", hashString).withPosition(0, 0);
            tab.add("Build Time", buildTime).withPosition(1, 0).withSize(2, 1);
            tab.add("Git Branch", branchString).withPosition(3, 0);
            tab.add("Git Files", fileString).withPosition(0, 1).withSize(4, 1);
            tab.add("MAC Address", macAddress).withPosition(4, 0);
        }
    }

    /**
     * Get an attribute, safely
     * 
     * @param attrs Attributes to load from
     * @param key   Key to load
     * @return An attribute, or UNKNOWN_VALUE.
     */
    private static String getAttr(final Attributes attrs, final String key) {
        if (attrs.containsKey(key)) {
            return attrs.getValue(key);
        } else {
            return CommandRobot.UNKNOWN_VALUE;
        }
    }

}
