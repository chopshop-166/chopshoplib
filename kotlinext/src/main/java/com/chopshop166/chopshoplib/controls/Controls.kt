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

    val a = controller.a()
    val b = controller.b()
    val x = controller.x()
    val y = controller.y()
    val start = controller.start()
    val back = controller.back()
    val l = controller.lbumper()
    val r = controller.rbumper()
    val leftStick = controller.lstick()
    val rightStick = controller.rstick()
    val axis = object {
        fun get(axis : Axis) = controller.getAxis(axis)
    }
}

operator fun ButtonXboxController.invoke(block : ControllerConfigurer.() -> Unit) = ControllerConfigurer(this).apply(block)
