package com.chopshop166.chopshoplib.commands

import edu.wpi.first.wpilibj2.command.ConditionalCommand
import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.Commands.*

fun testSequence() =
    sequence(
        PrintCommand("A"),
        parallel(
            PrintCommand("B"),
            PrintCommand("C").withTimeout(2.0),
            sequence(
                PrintCommand("F1"),
                waitSeconds(Math.PI),
                PrintCommand("F2"),
                runOnce {
                    System.out.println("2 + 2 = " + (2 + 2))
                }
            )
        ),
        PrintCommand("D"),
        PrintCommand("E").withTimeout(3.0),
        PrintCommand("G"),
        either(PrintCommand("It's true"), PrintCommand("It's false")) { -> true }
    )
