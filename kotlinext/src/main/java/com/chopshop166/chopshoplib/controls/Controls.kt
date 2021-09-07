package com.chopshop166.chopshoplib.controls

import com.chopshop166.chopshoplib.controls.ButtonXboxController
import com.chopshop166.chopshoplib.triggers.XboxTrigger
import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.XboxController.Button
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import edu.wpi.first.wpilibj2.command.button.Trigger

typealias TriggerSetup = Trigger.() -> Unit
typealias ButtonSetup = JoystickButton.() -> Unit

class ControllerConfigurer(val controller : ButtonXboxController) {

    private fun button(btn : Button) = JoystickButton(controller, btn.value)

    operator fun JoystickButton.invoke(block : ButtonSetup) = block()
    operator fun XboxTrigger.invoke(block : TriggerSetup) = block()

    val a = button(Button.kA)
    val b = button(Button.kB)
    val x = button(Button.kX)
    val y = button(Button.kY)
    val start = button(Button.kStart)
    val back = button(Button.kBack)
    val l = button(Button.kBumperLeft)
    val r = button(Button.kBumperRight)
    val stickLeft = button(Button.kStickLeft)
    val stickRight = button(Button.kStickRight)
    val trigger = mapOf(
        Hand.kLeft to XboxTrigger(controller, Hand.kLeft),
        Hand.kRight to XboxTrigger(controller, Hand.kRight)
    )
}

fun ButtonXboxController.configure(block : ControllerConfigurer.() -> Unit) = ControllerConfigurer(this).block()
