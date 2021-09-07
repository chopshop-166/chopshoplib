package com.chopshop166.chopshoplib.commands

import edu.wpi.first.wpilibj2.command.ConditionalCommand
import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.CommandGroupBase.sequence
import edu.wpi.first.wpilibj2.command.CommandGroupBase.parallel
import edu.wpi.first.wpilibj2.command.CommandGroupBase.race
import edu.wpi.first.wpilibj2.command.CommandGroupBase.deadline
import edu.wpi.first.wpilibj2.command.WaitCommand
import java.util.function.BooleanSupplier

fun testSequence() =
    sequence(
        PrintCommand("A"),
        parallel(
            PrintCommand("B"),
            PrintCommand("C").withTimeout(2.0),
            sequence(
                PrintCommand("F1"),
                wait(Math.PI),
                PrintCommand("F2"),
                exec {
                    System.out.println("2 + 2 = " + (2 + 2))
                }
            )
        ),
        PrintCommand("D"),
        PrintCommand("E").withTimeout(3.0),
        PrintCommand("G"),
        ConditionalCommand(PrintCommand("It's true"), PrintCommand("It's false"), BooleanSupplier { -> true })
    )
