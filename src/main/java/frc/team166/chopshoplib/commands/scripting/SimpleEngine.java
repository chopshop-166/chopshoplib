package frc.team166.chopshoplib.commands.scripting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.team166.chopshoplib.commands.CommandChain;
import frc.team166.chopshoplib.commands.TimeoutCommand;

/**
 * A simple language meant for creating series of commands Sets of commands are
 * separated with semicolons Commands to be run at the same time are separated
 * by pipes Command names are separated from their optional double arguments by
 * whitespace A command can be given a timeout with `timeout 5 mycommand`
 */
public class SimpleEngine implements Engine {
    private final Map<String, Function<String, Command>> handlers = new HashMap<String, Function<String, Command>>();

    /**
     * Initialize the default handlers
     */
    public SimpleEngine() {
        register("wait", WaitCommand::new);
        registerHandler("print", PrintCommand::new);
    }

    /**
     * Register a command function with the given prefix
     * 
     * @param prefix
     *            The prefix for use in scripts
     * @param func
     *            The function that creates the given command, given a double
     *            parameter
     */
    @Override
    public void registerHandler(final String prefix, final Function<String, Command> func) {
        handlers.put(prefix, func);
    }

    /**
     * Unregister a command function with the given prefix
     *
     * If no new command is specified for this prefix, its usage in scripts will be
     * an error
     * 
     * @param prefix
     *            The prefix for use in scripts
     */
    @Override
    public void unregister(final String prefix) {
        handlers.remove(prefix);
    }

    /**
     * Parse the entire script into a command group
     */
    @Override
    public Command parseScript(final String script) {
        final CommandChain result = new CommandChain(script);
        if (!"".equals(script)) {
            for (final String groupStr : script.trim()
                    .split(";")) {
                final Command[] cmds = Arrays.stream(groupStr.split("\\|"))
                        .map(String::trim)
                        .map(this::parseSingleCommand)
                        .toArray(Command[]::new);
                result.then(cmds);
            }
        }
        return result;
    }

    /**
     * Create a command from a string
     * 
     * @param cmd
     *            The name of the command to look up
     */
    private Command parseSingleCommand(final String cmd) {
        Command resultCommand = null;
        final String[] args = cmd.split("[\\s,]+");

        if (args.length > 2 && args[0].equals("timeout")) {
            final double timeoutLength = Double.parseDouble(args[1]);
            final String[] cmdargs = Arrays.copyOfRange(args, 2, args.length);
            final Command wrapped = parseArgs(cmdargs);
            if (wrapped != null) {
                resultCommand = new TimeoutCommand(timeoutLength, wrapped);
            }
        } else if (args.length != 0) {
            resultCommand = parseArgs(args);
        }
        return resultCommand;
    }

    /**
     * Create a command from a string
     * 
     * @param args
     *            The split arguments, including command name
     */
    private Command parseArgs(final String... args) {
        final Function<String, Command> constructor = handlers.get(args[0]);
        Command resultCommand = null;
        if (constructor != null) {
            String argument = "";
            if (args.length > 1) {
                argument = args[1];
            }
            resultCommand = constructor.apply(argument);
        }
        return resultCommand;
    }
}
