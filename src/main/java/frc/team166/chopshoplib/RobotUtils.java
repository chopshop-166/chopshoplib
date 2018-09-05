package frc.team166.chopshoplib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class RobotUtils {
    private RobotUtils() {
    }

    public static void clearPreferences() {
        final Preferences prefs = Preferences.getInstance();
        for (final String key : prefs.getKeys()) {
            prefs.remove(key);
        }
    }

    public static Double[] toBoxed(final double... args) {
        return DoubleStream.of(args).boxed().toArray(Double[]::new);
    }

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

    private static String getResource(final String path) {
        try (InputStream stream = RobotUtils.class.getResourceAsStream("/" + path);
                InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(reader)) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            return "";
        }
    }

    public static void resetAll(final TimedRobot robot) {
        final Class<? extends TimedRobot> clazz = robot.getClass();

        for (final Field field : clazz.getDeclaredFields()) {
            // Make the field accessible, because apparently we're allowed to do that
            field.setAccessible(true);
            try {
                // See if the returned object implements resettable.
                // If it does, then reset it.
                // This should help prevent the robot from taking off unexpectedly
                if (Resettable.class.isAssignableFrom(field.getType())) {
                    final Resettable resettable = (Resettable) field.get(robot);
                    resettable.reset();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}