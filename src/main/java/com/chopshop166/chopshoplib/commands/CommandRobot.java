package com.chopshop166.chopshoplib.commands;

import static com.chopshop166.chopshoplib.RobotUtils.getValueOrDefault;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.chopshop166.chopshoplib.Resettable;
import com.chopshop166.chopshoplib.RobotUtils;
import com.chopshop166.chopshoplib.maps.RobotMapFor;
import com.chopshop166.chopshoplib.maps.RobotMapper;
import com.google.common.io.Resources;
import com.google.common.reflect.ClassPath;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

/**
 * A Robot that calls the command scheduler in its periodic functions.
 * 
 * Contains convenient wrappers for the commands that are often used in groups.
 */
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

    /** Add all the autonomous modes to the chooser. */
    public abstract void populateAutonomous();

    @Override
    public void robotInit() {
        super.robotInit();
        logBuildData();
        configureButtonBindings();
        populateDashboard();
        setDefaultCommands();

        populateAutonomous();
        Shuffleboard.getTab("Shuffleboard").add("Autonomous", autoChooser);
    }

    @Override
    public void robotPeriodic() {
        // Do not call the super method, remove the annoying print
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        resetAll();
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This autonomous runs the autonomous command selected by the chooser.
     */
    @Override
    public void autonomousInit() {
        autoCmd = getAutoCommand();

        // schedule the autonomous command (example)
        if (autoCmd != null) {
            autoCmd.schedule();
        }
    }

    @Override
    public void teleopInit() {
        if (autoCmd != null) {
            autoCmd.cancel();
        }
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * Get the autonomous command to run.]
     * 
     * By default, this is from a SendableChooser.
     * 
     * @return A {@link Command} object
     */
    public Command getAutoCommand() {
        return autoChooser.getSelected();
    }

    /**
     * Add an autonomous option.
     * 
     * By default, this adds to the internal Chooser.
     * 
     * @param name The name to give the autonomous.
     * @param auto The command to run.
     */
    public void addAutonomous(final String name, final Command auto) {
        autoChooser.addOption(name, auto);
    }

    /**
     * Factory for robot mappers.
     * 
     * @param <T> The root type for the mapper.
     * @return The mapper.
     */
    public static <T> RobotMapper<T> mapper() {
        return new RobotMapper<T>();
    }

    /**
     * Reset all {@link Resettable} objects within this robot.
     */
    public void resetAll() {
        final Class<? extends RobotBase> clazz = getClass();

        for (final Field field : clazz.getDeclaredFields()) {
            // Make the field accessible, because apparently we're allowed to do that
            field.setAccessible(true);
            try {
                // See if the returned object implements resettable.
                // If it does, then reset it.
                // This should help prevent the robot from taking off unexpectedly
                if (Resettable.class.isAssignableFrom(field.getType())) {
                    final Resettable resettable = (Resettable) field.get(this);
                    resettable.reset();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create a command builder with a given name.
     * 
     * @param name The command name.
     * @return A new command builder.
     */
    public static CommandBuilder cmd(final String name) {
        return new CommandBuilder(name);
    }

    /**
     * Create a {@link FunctionalCommand} with a name.
     * 
     * @param name       The command name.
     * @param onInit     The action to run on initialize. If null, then will do
     *                   nothing.
     * @param onExecute  The action to run on execute. If null, then will do
     *                   nothing.
     * @param onEnd      The action to run on end. If null, then will do nothing.
     * @param isFinished The condition to end the command.
     * @return A new command.
     */
    public static CommandBase functional(final String name, final Runnable onInit, final Runnable onExecute,
            final Consumer<Boolean> onEnd, final BooleanSupplier isFinished) {
        final Runnable realOnInit = getValueOrDefault(onInit, () -> {
        });
        final Runnable realOnExec = getValueOrDefault(onExecute, () -> {
        });
        final Consumer<Boolean> realOnEnd = getValueOrDefault(onEnd, interrupted -> {
        });
        final BooleanSupplier realIsFinished = getValueOrDefault(isFinished, () -> true);
        return new FunctionalCommand(realOnInit, realOnExec, realOnEnd, realIsFinished).withName(name);
    }

    /**
     * Create an {@link InstantCommand}.
     * 
     * @param name   The name of the command.
     * @param action The action to take.
     * @return A new command.
     */
    public static CommandBase instant(final String name, final Runnable action) {
        return new InstantCommand(action).withName(name);
    }

    /**
     * Create a {@link RunCommand}.
     * 
     * @param name   The name of the command.
     * @param action The action to take.
     * @return A new command.
     */
    public static CommandBase running(final String name, final Runnable action) {
        return new RunCommand(action).withName(name);
    }

    /**
     * Create a {@link StartEndCommand}.
     * 
     * @param name    The name of the command.
     * @param onStart The action to take on start.
     * @param onEnd   The action to take on end.
     * @return A new command.
     */
    public static CommandBase startEnd(final String name, final Runnable onStart, final Runnable onEnd) {
        return new StartEndCommand(onStart, onEnd).withName(name);
    }

    /**
     * Run a {@link Runnable} and then wait until a condition is true.
     * 
     * @param name  The name of the command.
     * @param init  The action to take.
     * @param until The condition to wait until.
     * @return A new command.
     */
    public static CommandBase initAndWait(final String name, final Runnable init, final BooleanSupplier until) {
        return parallel(name, new InstantCommand(init), new WaitUntilCommand(until));
    }

    /**
     * Create a command to call a consumer function.
     * 
     * @param <T>   The type to wrap.
     * @param name  The name of the command.
     * @param value The value to call the function with.
     * @param func  The function to call.
     * @return A new command.
     */
    public static <T> CommandBase setter(final String name, final T value, final Consumer<T> func) {
        return new InstantCommand(() -> {
            func.accept(value);
        }).withName(name);
    }

    /**
     * Create a command to call a consumer function and wait.
     * 
     * @param <T>   The type to wrap.
     * @param name  The name of the command.
     * @param value The value to call the function with.
     * @param func  The function to call.
     * @param until The condition to wait until.
     * @return A new command.
     */
    public static <T> CommandBase callAndWait(final String name, final T value, final Consumer<T> func,
            final BooleanSupplier until) {
        return initAndWait(name, () -> {
            func.accept(value);
        }, until);
    }

    /**
     * Create a sequential command group with a name.
     * 
     * @param name The name of the command group.
     * @param cmds The commands to sequence.
     * @return A new command group.
     */
    public static CommandBase sequence(final String name, final Command... cmds) {
        return CommandGroupBase.sequence(cmds).withName(name);
    }

    /**
     * Create a parallel command group with a name.
     * 
     * @param name The name of the command group.
     * @param cmds The commands to run in parallel.
     * @return A new command group.
     */
    public static CommandBase parallel(final String name, final Command... cmds) {
        return CommandGroupBase.parallel(cmds).withName(name);
    }

    /**
     * Create a racing command group with a name.
     * 
     * @param name   The name of the command group.
     * @param racers The commands to race.
     * @return A new command group.
     */
    public static CommandBase race(final String name, final Command... racers) {
        return CommandGroupBase.race(racers).withName(name);
    }

    /**
     * Create a deadline-limited command group with a name.
     * 
     * @param name    The name of the command group.
     * @param limiter The deadline command.
     * @param cmds    The commands to run until the deadline ends.
     * @return A new command group.
     */
    public static CommandBase deadline(final String name, final Command limiter, final Command... cmds) {
        return CommandGroupBase.deadline(limiter, cmds).withName(name);
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
        return getRobotMap(rootClass, pkg, null);
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
        String macAddress = UNKNOWN_VALUE;

        try {
            final URL manifestURL = Resources.getResource("META-INF/MANIFEST.MF");
            final Manifest manifest = new Manifest(manifestURL.openStream());
            final Attributes attrs = manifest.getMainAttributes();

            hashString = getAttr(attrs, "Git-Hash");
            buildTime = getAttr(attrs, "Build-Time");
            branchString = getAttr(attrs, "Git-Branch");
            fileString = getAttr(attrs, "Git-Files");
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
            return UNKNOWN_VALUE;
        }
    }

}
