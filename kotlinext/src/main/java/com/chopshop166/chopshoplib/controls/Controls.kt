package com.chopshop166.chopshoplib.controls

import com.chopshop166.chopshoplib.controls.ButtonXboxController
import edu.wpi.first.wpilibj.XboxController.Axis
import edu.wpi.first.wpilibj2.command.button.Trigger

class ControllerConfigurer(val controller : ButtonXboxController) {

    operator fun Trigger.invoke(block : Trigger.() -> Unit) = block()

    val a = controller.a()
    val b = controller.b()
    val x = controller.x()
    val y = controller.y()
    val start = controller.start()
    val back = controller.back()
    val l = controller.leftBumper()
    val r = controller.rightBumper()
    val leftStick = controller.leftStick()
    val rightStick = controller.rightStick()
    val axis = object {
        fun get(axis : Axis) = controller.getAxis(axis)
    }
}

operator fun ButtonXboxController.invoke(block : ControllerConfigurer.() -> Unit) = ControllerConfigurer(this).apply(block)
