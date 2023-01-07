package com.chopshop166.chopshoplib.commands

import com.chopshop166.chopshoplib.HasSafeState
import com.chopshop166.chopshoplib.Resettable
import com.chopshop166.chopshoplib.commands.CommandRobot
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.ConditionalCommand
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.ProxyCommand
import edu.wpi.first.wpilibj2.command.RunCommand
import edu.wpi.first.wpilibj2.command.StartEndCommand
import edu.wpi.first.wpilibj2.command.SelectCommand
import edu.wpi.first.wpilibj2.command.Subsystem
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.WaitCommand
import edu.wpi.first.wpilibj2.command.WaitUntilCommand
import java.util.function.Supplier

@DslMarker
annotation class CommandBuilderMarker

@CommandBuilderMarker
class CommandBuilder(private vararg val sys: Subsystem) {
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
                addRequirements(*sys)
            }
            override fun initialize() = onInit()
            override fun execute() = onExecute()
            override fun end(interrupted: Boolean) = onEnd(interrupted)
            override fun isFinished() = isFinishedCheck()
        }
}

inline fun <reified T> CommandRobot.getMapForName(
    name: String,
    pkg: String,
    defaultValue: T
) =
    CommandRobot.getMapForName<T>(name, T::class.java, pkg, defaultValue)

fun repeat(num: Int, block: () -> Command) =
    Commands.sequence(*(0..num).map {block()}.toTypedArray())

fun Subsystem.cmd(block: CommandBuilder.() -> Unit) =
    CommandBuilder(this).apply(block).build()

fun cmd(block: CommandBuilder.() -> Unit) =
    CommandBuilder().apply(block).build()

/**
 * Create an {@link InstantCommand}.
 * 
 * @param action The action to take.
 * @return A new command.
 */
fun Subsystem.runOnce(action : () -> Unit) =
    Commands.runOnce(action, this)

/**
 * Create an {@link InstantCommand}.
 * 
 * @param action The action to take.
 * @return A new command.
 */
fun runOnce(action : () -> Unit) =
    Commands.runOnce(action)

/**
 * Create a {@link RunCommand}.
 * 
 * @param action The action to take.
 * @return A new command.
 */
fun Subsystem.run(action : () -> Unit) =
    Commands.run(action, this)

/**
 * Create a {@link RunCommand}.
 * 
 * @param action The action to take.
 * @return A new command.
 */
fun run(action : () -> Unit) =
    Commands.run(action)

/**
 * Create a {@link StartEndCommand}.
 * 
 * @param onStart The action to take on start.
 * @param onEnd   The action to take on end.
 * @return A new command.
 */
fun Subsystem.startEnd(onStart : () -> Unit, onEnd : () -> Unit) =
    StartEndCommand(onStart, onEnd, this)

/**
 * Create a {@link StartEndCommand}.
 * 
 * @param onStart The action to take on start.
 * @param onEnd   The action to take on end.
 * @return A new command.
 */
fun startEnd(onStart : () -> Unit, onEnd : () -> Unit) =
    StartEndCommand(onStart, onEnd)

/**
 * Run a {@link Runnable} and then wait until a condition is true.
 * 
 * @param init  The action to take.
 * @param until The condition to wait until.
 * @return A new command.
 */
fun Subsystem.initAndWait(init : () -> Unit, until : () -> Boolean) =
    Commands.parallel(Commands.runOnce(init, this), Commands.waitUntil(until))

/**
 * Run a {@link Runnable} and then wait until a condition is true.
 * 
 * @param init  The action to take.
 * @param until The condition to wait until.
 * @return A new command.
 */
fun initAndWait(init : () -> Unit, until : () -> Boolean) =
    Commands.parallel(Commands.runOnce(init), Commands.waitUntil(until))

/**
 * Create a command to call a consumer function.
 * 
 * @param <T>   The type to wrap.
 * @param value The value to call the function with.
 * @param func  The function to call.
 * @return A new command.
 */
fun <T> Subsystem.setter(value : T, func : (T) -> Unit) =
    runOnce {
        func(value)
    }

/**
 * Create a command to call a consumer function.
 * 
 * @param <T>   The type to wrap.
 * @param value The value to call the function with.
 * @param func  The function to call.
 * @return A new command.
 */
fun <T> setter(value : T, func : (T) -> Unit) =
    runOnce {
        func(value)
    }

/**
 * Create a command to call a consumer function and wait.
 * 
 * @param <T>   The type to wrap.
 * @param value The value to call the function with.
 * @param func  The function to call.
 * @param until The condition to wait until.
 * @return A new command.
 */
fun <T> Subsystem.callAndWait(value : T, func : (T) -> Unit, until : () -> Boolean) =
    initAndWait({
        func(value);
    }, until)

/**
 * Create a command to call a consumer function and wait.
 * 
 * @param <T>   The type to wrap.
 * @param value The value to call the function with.
 * @param func  The function to call.
 * @param until The condition to wait until.
 * @return A new command.
 */
fun <T> callAndWait(value : T, func : (T) -> Unit, until : () -> Boolean) =
    initAndWait({
        func(value);
    }, until)

/**
 * Create a command that runs only if a condition is true.
 * 
 * @param condition The condition to test beforehand.
 * @param cmd       The command to run.
 * @return The conditional command.
 */
fun CommandBase.runsIf(condition : () -> Boolean) =
    Commands.either(this, Commands.none(), condition)

/**
 * Create a command that selects which command to run from a map.
 * 
 * @param commands The possible commands to run.
 * @param selector The function to determine which command should be run.
 * @return The wrapper command object.
 */
fun select(commands : Map<Any, Command>, selector: () -> Any) =
    SelectCommand(commands, selector)

/**
 * Create a command that selects which command to run from a function.
 * 
 * @param selector The function to determine which command should be run.
 * @return The wrapper command object.
 */
fun select(selector : () -> Command) =
    ProxyCommand(selector)

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
 * Create a command to run at regular intervals.
 * 
 * @param timeDelta Time in seconds to wait between calls.
 * @param periodic  The runnable to execute.
 * @return A new command.
 */
fun every(timeDelta : Double, periodic : () -> Unit) =
    IntervalCommand(timeDelta, periodic)

/**
 * Create a command to reset the subsystem sensors.
 * 
 * @return A command.
 */
fun <T> T.resetCmd() where T : SubsystemBase, T : Resettable =
    this.runOnce(this::reset).withName("Reset " + this.name)

/**
 * Create a command to set the subsystem to a safe state.
 * 
 * @return A command.
 */
fun <T> T.safeStateCmd() where T : SubsystemBase, T : HasSafeState =
    this.runOnce(this::safeState).withName("Safe " + this.name)

/**
 * Cancel the currently running command.
 * 
 * @return A cancel command.
 */
fun SubsystemBase.cancel() =
    this.runOnce {
    }.withName("Cancel " + this.name)