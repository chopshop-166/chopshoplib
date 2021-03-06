package com.chopshop166.chopshoplib.controls

import com.chopshop166.chopshoplib.controls.ButtonXboxController
import com.chopshop166.chopshoplib.triggers.XboxTrigger

import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.XboxController.Button
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import edu.wpi.first.wpilibj2.command.button.Trigger

typealias TriggerSetup = Trigger.() -> Unit
typealias ButtonSetup = JoystickButton.() -> Unit

class ControllerConfigurer(val controller : ButtonXboxController) {
    private fun button(btn : Button, block : ButtonSetup) = btn.also {
        JoystickButton(controller, btn.value).block()
    }

    fun a(block : ButtonSetup) = button(Button.kA, block)
    fun b(block : ButtonSetup) = button(Button.kB, block)
    fun x(block : ButtonSetup) = button(Button.kX, block)
    fun y(block : ButtonSetup) = button(Button.kY, block)
    fun start(block : ButtonSetup) = button(Button.kStart, block)
    fun back(block : ButtonSetup) = button(Button.kBack, block)
    fun l(block : ButtonSetup) = button(Button.kBumperLeft, block)
    fun r(block : ButtonSetup) = button(Button.kBumperRight, block)
    fun stickLeft(block : ButtonSetup) = button(Button.kStickLeft, block)
    fun stickRight(block : ButtonSetup) = button(Button.kStickRight, block)
    fun trigger(hand : Hand, block : TriggerSetup) = XboxTrigger(controller, hand).block()
}

fun ButtonXboxController.configure(block : ControllerConfigurer.() -> Unit) {
    ControllerConfigurer(this).block()
}
