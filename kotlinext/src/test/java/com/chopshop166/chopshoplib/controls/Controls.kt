package com.chopshop166.chopshoplib.controls

import com.chopshop166.chopshoplib.controls.ButtonXboxController
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.PrintCommand

fun configureTest(controller : ButtonXboxController) {
    controller {
        a {
            whenPressed(PrintCommand("A pressed"))
        }
        b {
            whenPressed(PrintCommand("B pressed"))
            whenReleased(PrintCommand("B released"))
        }
        x.whenPressed(PrintCommand("X pressed"))
    }
}
