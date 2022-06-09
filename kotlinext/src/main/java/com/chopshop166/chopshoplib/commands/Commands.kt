package com.chopshop166.chopshoplib.commands

import com.chopshop166.chopshoplib.HasSafeState
import com.chopshop166.chopshoplib.Resettable
import com.chopshop166.chopshoplib.commands.CommandRobot
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.CommandGroupBase
import edu.wpi.first.wpilibj2.command.ConditionalCommand
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.RunCommand
import edu.wpi.first.wpilibj2.command.StartEndCommand
import edu.wpi.first.wpilibj2.command.SelectCommand
import edu.wpi.first.wpilibj2.command.Subsystem
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.WaitCommand
import edu.wpi.first.wpilibj2.command.WaitUntilCommand
import java.util.function.Supplier

fun wait(time: Double) = WaitCommand(time)
fun wait(until: () -> Boolean) = WaitUntilCommand(until)

fun exec(block: () -> Unit) = InstantCommand(block)

fun repeat(name: String = "", num: Int, block: () -> Command) =
    sequence(name, *(0..num).map {block()}.toTypedArray())

@DslMarker
annotation class CommandBuilderMarker

@CommandBuilderMarker
class CommandBuilder(var cmdName: String = "", private vararg val sys: Subsystem) {
    private var onInit: Command.() -> Unit = {}
    private var onExecute: Command.() -> Unit = {}
    private var onEnd: Command.(Boolean) -> Unit = {}
    private var isFinishedCheck: Command.() -> Boolean = { true }

    fun initialize(onInit: Command.() -> Unit) { this.onInit = onInit }
    fun execute(onExecute: Command.() -> Unit) { this.onExecute = onExecute }
    fun end(onEnd: Command.(Boolean) -> Unit) { this.onEnd = onEnd }
    fun isFinished(isFinishedCheck: Command.() -> Boolean) { this.isFinishedCheck = isFinishedCheck }

    fun build() =
        object : CommandBase() {
            init {
                if (cmdName != "") {
                    this.name = cmdName
                }
                addRequirements(*sys)
            }
            override fun initialize() = onInit()
            override fun execute() = onExecute()
            override fun end(interrupted: Boolean) = onEnd(interrupted)
            override fun isFinished() = isFinishedCheck()
        }
}

fun cmd(name: String = "", block: CommandBuilder.() -> Unit) =
    CommandBuilder(name).apply(block).build()

inline fun <reified T> CommandRobot.getMapForName(
    name: String,
    pkg: String,
    defaultValue: T
) =
    CommandRobot.getMapForName<T>(name, T::class.java, pkg, defaultValue)

fun Subsystem.cmd(name: String = "", block: CommandBuilder.() -> Unit) =
    CommandBuilder(name, this).apply(block).build()

/**
 * Create an {@link InstantCommand}.
 * 
 * @param name   The name of the command.
 * @param action The action to take.
 * @return A new command.
 */
fun Subsystem.instant(name : String, action : () -> Unit) =
    InstantCommand(action, this).withName(name)

/**
 * Create an {@link InstantCommand}.
 * 
 * @param name   The name of the command.
 * @param action The action to take.
 * @return A new command.
 */
fun instant(name : String, action : () -> Unit) =
    InstantCommand(action).withName(name)

/**
 * Create a {@link RunCommand}.
 * 
 * @param name   The name of the command.
 * @param action The action to take.
 * @return A new command.
 */
fun Subsystem.running(name : String, action : () -> Unit) =
    RunCommand(action, this).withName(name)

/**
 * Create a {@link RunCommand}.
 * 
 * @param name   The name of the command.
 * @param action The action to take.
 * @return A new command.
 */
fun running(name : String, action : () -> Unit) =
    RunCommand(action).withName(name)

/**
 * Create a {@link StartEndCommand}.
 * 
 * @param name    The name of the command.
 * @param onStart The action to take on start.
 * @param onEnd   The action to take on end.
 * @return A new command.
 */
fun Subsystem.startEnd(name : String, onStart : () -> Unit, onEnd : () -> Unit) =
    StartEndCommand(onStart, onEnd, this).withName(name)

/**
 * Create a {@link StartEndCommand}.
 * 
 * @param name    The name of the command.
 * @param onStart The action to take on start.
 * @param onEnd   The action to take on end.
 * @return A new command.
 */
fun startEnd(name : String, onStart : () -> Unit, onEnd : () -> Unit) =
    StartEndCommand(onStart, onEnd,).withName(name)

/**
 * Wait until a condition is true.
 * 
 * @param name  The name of the command.
 * @param until The condition to wait until.
 * @return A new command.
 */
fun waitUntil(name : String, until : () -> Boolean) =
    parallel(name, wait(until))

/**
 * Run a {@link Runnable} and then wait until a condition is true.
 * 
 * @param name  The name of the command.
 * @param init  The action to take.
 * @param until The condition to wait until.
 * @return A new command.
 */
fun Subsystem.initAndWait(name : String, init : () -> Unit, until : () -> Boolean) =
    parallel(name, InstantCommand(init, this), wait(until))

/**
 * Run a {@link Runnable} and then wait until a condition is true.
 * 
 * @param name  The name of the command.
 * @param init  The action to take.
 * @param until The condition to wait until.
 * @return A new command.
 */
fun initAndWait(name : String, init : () -> Unit, until : () -> Boolean) =
    parallel(name, InstantCommand(init), wait(until))

/**
 * Create a command to call a consumer function.
 * 
 * @param <T>   The type to wrap.
 * @param name  The name of the command.
 * @param value The value to call the function with.
 * @param func  The function to call.
 * @return A new command.
 */
fun <T> Subsystem.setter(name : String, value : T, func : (T) -> Unit) =
    instant(name) {
        func(value)
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
fun <T> setter(name : String, value : T, func : (T) -> Unit) =
    instant(name) {
        func(value)
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
fun <T> Subsystem.callAndWait(name : String, value : T, func : (T) -> Unit, until : () -> Boolean) =
    initAndWait(name, {
        func(value);
    }, until)

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
fun <T> callAndWait(name : String, value : T, func : (T) -> Unit, until : () -> Boolean) =
    initAndWait(name, {
        func(value);
    }, until)

/**
 * Create a conditional command.
 * 
 * @param condition The condition to test.
 * @param onTrue    The command to run if the condition is true.
 * @param onFalse   The command to run if the condition is false.
 * @return The conditional command.
 */
fun conditional(condition : () -> Boolean, onTrue : Command, onFalse : Command) =
    ConditionalCommand(onTrue, onFalse, condition)

/**
 * Create a command that runs only if a condition is true.
 * 
 * @param condition The condition to test beforehand.
 * @param cmd       The command to run.
 * @return The conditional command.
 */
fun runIf(condition : () -> Boolean, cmd : Command) =
    conditional(condition, cmd, InstantCommand())

/**
 * Create a command that selects which command to run from a map.
 * 
 * @param name     The command's name.
 * @param commands The possible commands to run.
 * @param selector The function to determine which command should be run.
 * @return The wrapper command object.
 */
fun select(name : String, commands : Map<Any, Command>, selector: () -> Any) =
    SelectCommand(commands, selector).withName(name)

/**
 * Create a command that selects which command to run from a function.
 * 
 * @param name     The command's name.
 * @param selector The function to determine which command should be run.
 * @return The wrapper command object.
 */
fun select(name : String, selector : () -> Command) =
    SelectCommand(selector).withName(name)

/**
 * Create a command to run at regular intervals.
 * 
 * @param timeDelta Time in seconds to wait between calls.
 * @param periodic  The runnable to execute.
 * @return A new command.
 */
fun every(timeDelta : Double, periodic : () -> Unit) =
    IntervalCommand(timeDelta, periodic)

/**
 * Create a command to run at regular intervals.
 * 
 * @param timeDelta Time in seconds to wait between calls.
 * @param periodic  The runnable to execute.
 * @return A new command.
 */
fun Subsystem.every(timeDelta : Double, periodic : () -> Unit) =
    IntervalCommand(timeDelta, this, periodic)

/**
 * Create a command to reset the subsystem sensors.
 * 
 * @return A command.
 */
fun <T> T.resetCmd() where T : SubsystemBase, T : Resettable =
    instant("Reset " + this.name, this::reset)

/**
 * Create a command to set the subsystem to a safe state.
 * 
 * @return A command.
 */
fun <T> T.safeStateCmd() where T : SubsystemBase, T : HasSafeState =
    instant("Reset " + this.name, this::safeState)

/**
 * Cancel the currently running command.
 * 
 * @return A cancel command.
 */
fun SubsystemBase.cancel() =
    instant("Cancel " + this.name) {
    }

/**
 * Create a sequential command group with a name.
 * 
 * @param name The name of the command group.
 * @param cmds The commands to sequence.
 * @return A new command group.
 */
fun sequence(name : String, vararg cmds : Command) =
    CommandGroupBase.sequence(*cmds).withName(name)

/**
 * Create a parallel command group with a name.
 * 
 * @param name The name of the command group.
 * @param cmds The commands to run in parallel.
 * @return A new command group.
 */
fun parallel(name : String, vararg cmds : Command) =
    CommandGroupBase.parallel(*cmds).withName(name)

/**
 * Create a racing command group with a name.
 * 
 * @param name   The name of the command group.
 * @param racers The commands to race.
 * @return A new command group.
 */
fun race(name : String, vararg racers : Command) =
    CommandGroupBase.race(*racers).withName(name)

/**
 * Create a deadline-limited command group with a name.
 * 
 * @param name    The name of the command group.
 * @param limiter The deadline command.
 * @param cmds    The commands to run until the deadline ends.
 * @return A new command group.
 */
fun deadline(name : String, limiter : Command, vararg cmds : Command) =
    CommandGroupBase.deadline(limiter, *cmds).withName(name)