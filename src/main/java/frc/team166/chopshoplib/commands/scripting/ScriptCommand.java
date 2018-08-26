package frc.team166.chopshoplib.commands.scripting;

import java.util.function.Function;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * An autonomous that is parsed from a string script
 */
public class ScriptCommand extends Command {

    private String script;
    private Engine engine;
    private Command generatedCommand;

    public static final Engine DEFAULT_ENGINE = new SimpleEngine();

    /**
     * Create an autonomous that parses the given script when it's started
     * 
     * @param script
     *            The script string to evaluate
     */
    public ScriptCommand(final String script) {
        this(script, DEFAULT_ENGINE);
    }

    /**
     * Create an autonomous that parses the given script when it's started
     * 
     * @param name
     *            The name of the command
     * @param script
     *            The script string to evaluate
     */
    public ScriptCommand(final String name, final String script) {
        this(name, script, DEFAULT_ENGINE);
        this.script = script;
    }

    /**
     * Create an autonomous that parses the given script when it's started
     * 
     * @param script
     *            The script string to evaluate
     * @param engine
     *            The scripting engine to use for this command
     */
    public ScriptCommand(final String script, final Engine engine) {
        super();
        this.script = script;
        this.engine = engine;
    }

    /**
     * Create an autonomous that parses the given script when it's started
     * 
     * @param name
     *            The name of the command
     * @param script
     *            The script string to evaluate
     * @param engine
     *            The scripting engine to use for this command
     */
    public ScriptCommand(final String name, final String script, final Engine engine) {
        super(name);
        this.script = script;
        this.engine = engine;
    }

    /**
     * Create a command that parses and runs a ScriptCommand from a preference
     * 
     * @param key
     *            The preference name to read from
     * @param engine
     *            The scripting engine to use
     */
    public static Command fromPreference(final String key, final Engine engine) {
        final Preferences prefs = Preferences.getInstance();
        if (!prefs.containsKey(key)) {
            prefs.putString(key, "");
        }
        return new InstantCommand(key) {
            @Override
            protected void initialize() {
                final String script = Preferences.getInstance()
                        .getString(key, "");
                final Command cmd = new ScriptCommand(key, script, engine);
                cmd.start();
            }
        };
    }

    /**
     * Create a command that parses and runs a ScriptCommand from a preference
     * 
     * @param key
     *            The preference name to read from
     */
    public static Command fromPreference(final String key) {
        return fromPreference(key, DEFAULT_ENGINE);
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
    static public void registerHandler(final String prefix, final Function<String, Command> func) {
        DEFAULT_ENGINE.registerHandler(prefix, func);
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
    static public void register(final String prefix, final Function<Double, Command> func) {
        DEFAULT_ENGINE.register(prefix, func);
    }

    /**
     * Register a command function with the given prefix
     * 
     * @param prefix
     *            The prefix for use in scripts
     * @param func
     *            The function that creates the given command
     */
    static public void register(final String prefix, final Supplier<Command> func) {
        DEFAULT_ENGINE.register(prefix, func);
    }

    /**
     * Register a scriptable object
     * 
     * Calls the object's registerHandler method
     * 
     * @param scriptable
     *            The object to use in scripts
     */
    static public void register(final Scriptable scriptable) {
        DEFAULT_ENGINE.register(scriptable);
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
    static public void unregister(final String prefix) {
        DEFAULT_ENGINE.unregister(prefix);
    }

    @Override
    protected void initialize() {
        generatedCommand = engine.parseScript(script);
        generatedCommand.start();
    }

    @Override
    protected boolean isFinished() {
        return generatedCommand != null
                && (generatedCommand.isCanceled() || generatedCommand.isCompleted());
    }

}
