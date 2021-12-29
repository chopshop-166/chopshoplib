package com.chopshop166.chopshoplib.controls

import com.chopshop166.chopshoplib.controls.ButtonXboxController
import com.chopshop166.chopshoplib.triggers.AxisButton
import edu.wpi.first.wpilibj.XboxController.Axis
import edu.wpi.first.wpilibj.XboxController.Button
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import edu.wpi.first.wpilibj2.command.button.Trigger

class ControllerConfigurer(val controller : ButtonXboxController) {

    operator fun AxisButton.invoke(block : AxisButton.() -> Unit) = block()
    operator fun JoystickButton.invoke(block : JoystickButton.() -> Unit) = block()

    val a = controller.getButton(Button.kA)
    val b = controller.getButton(Button.kB)
    val x = controller.getButton(Button.kX)
    val y = controller.getButton(Button.kY)
    val start = controller.getButton(Button.kStart)
    val back = controller.getButton(Button.kBack)
    val l = controller.getButton(Button.kLeftBumper)
    val r = controller.getButton(Button.kRightBumper)
    val leftStick = controller.getButton(Button.kLeftStick)
    val rightStick = controller.getButton(Button.kRightStick)
    val axis = object {
        fun get(axis : Axis) = controller.getAxis(axis)
    }
}

operator fun ButtonXboxController.invoke(block : ControllerConfigurer.() -> Unit) = ControllerConfigurer(this).apply(block)
