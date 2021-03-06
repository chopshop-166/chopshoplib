package com.chopshop166.chopshoplib.outputs

import com.chopshop166.chopshoplib.sensors.IEncoder
import com.chopshop166.chopshoplib.sensors.MockEncoder
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import com.revrobotics.ControlType
import edu.wpi.first.wpilibj.Sendable
import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry

enum class PIDControlType {
    Velocity,
    Position
}

fun <T> T.toSSC(encoder: IEncoder = MockEncoder()) where T : Sendable, T : SpeedController =
    ModSpeedController(this, encoder)

fun CANSparkMax.follow(thisObj: PIDSparkMax, inverted : Boolean = false) = follow(thisObj.motorController, inverted)

fun CANSparkMax.withPID(
    controlType: PIDControlType = PIDControlType.Velocity,
    block: PIDSparkMax.() -> Unit = {}
) =
    PIDSparkMax(this).apply {
        this.setControlType(
                when (controlType) {
                    PIDControlType.Position -> ControlType.kPosition
                    PIDControlType.Velocity -> ControlType.kVelocity
                })
        block()
    }


fun <T> T.withPID(
    pid: PIDController,
    encoder: IEncoder,
    controlType: PIDControlType = PIDControlType.Velocity,
    block: SwPIDSpeedController.() -> Unit = {}
) where T : SpeedController, T : Sendable =
    when (controlType) {
        PIDControlType.Position -> SwPIDSpeedController.position(this, pid, encoder)
        PIDControlType.Velocity -> SwPIDSpeedController.velocity(this, pid, encoder)
    }.apply(block)

fun PIDSpeedController.toModSC(vararg mods: Modifier) =
    ModSpeedController(this, this.encoder).apply { mods.forEach(::add) }
