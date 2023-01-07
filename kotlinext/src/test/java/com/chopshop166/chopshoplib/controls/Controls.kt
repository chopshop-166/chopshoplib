package com.chopshop166.chopshoplib.controls

import com.chopshop166.chopshoplib.controls.ButtonXboxController
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.PrintCommand

fun configureTest(controller : ButtonXboxController) {
    controller {
        a {
            onTrue(PrintCommand("A pressed"))
        }
        b {
            onTrue(PrintCommand("B pressed"))
            onFalse(PrintCommand("B released"))
        }
        x.onTrue(PrintCommand("X pressed"))
    }
}
