package com.chopshop166.chopshoplib.outputs

import com.chopshop166.chopshoplib.sensors.IEncoder
import com.chopshop166.chopshoplib.sensors.MockEncoder
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.revrobotics.CANSparkMax
import com.revrobotics.ControlType
import edu.wpi.first.wpilibj.Sendable
import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj.controller.PIDController

enum class PIDControlType {
    Velocity,
    Position
}

fun SmartMotorController.toSmart() = this

fun <T> T.toSmart(encoder: IEncoder = MockEncoder(), vararg mods: Modifier) where
T : Sendable,
T : SpeedController = SmartMotorController(this, encoder, *mods)

fun CANSparkMax.follow(thisObj: PIDSparkMax, inverted: Boolean = false) =
        follow(thisObj.motorController, inverted)

fun CANSparkMax.withPID(
        controlType: PIDControlType = PIDControlType.Velocity,
        block: PIDSparkMax.() -> Unit = {}
) =
        PIDSparkMax(this).apply {
            this.controlType =
                    when (controlType) {
                        PIDControlType.Position -> ControlType.kPosition
                        PIDControlType.Velocity -> ControlType.kVelocity
                    }
            block()
        }

fun WPI_TalonSRX.withPID(
        controlType: PIDControlType = PIDControlType.Velocity,
        block: WPI_TalonSRX.() -> Unit = {}
) =
        PIDTalonSRX(this).apply {
            this.controlType =
                    when (controlType) {
                        PIDControlType.Position -> ControlMode.Position
                        PIDControlType.Velocity -> ControlMode.Velocity
                    }
            block()
        }

fun WPI_TalonFX.withPID(
        controlType: PIDControlType = PIDControlType.Velocity,
        block: WPI_TalonFX.() -> Unit = {}
) =
        PIDTalonFX(this).apply {
            this.controlType =
                    when (controlType) {
                        PIDControlType.Position -> ControlMode.Position
                        PIDControlType.Velocity -> ControlMode.Velocity
                    }
            block()
        }

fun <T> T.withPID(
        pid: PIDController,
        encoder: IEncoder,
        controlType: PIDControlType = PIDControlType.Velocity,
        block: SwPIDMotorController.() -> Unit = {}
) where T : SpeedController, T : Sendable =
        when (controlType) {
            PIDControlType.Position -> SwPIDMotorController.position(this, pid, encoder)
            PIDControlType.Velocity -> SwPIDMotorController.velocity(this, pid, encoder)
        }.apply(block)
