package com.chopshop166.chopshoplib.motors

import com.chopshop166.chopshoplib.sensors.IEncoder
import com.chopshop166.chopshoplib.sensors.MockEncoder
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.ctre.phoenix6.hardware.TalonFX
import com.revrobotics.CANSparkMax
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj.motorcontrol.MotorController

fun SmartMotorController.toSmart() = this

fun MotorController.toSmart(encoder: IEncoder = MockEncoder()) =
    SmartMotorController(this, encoder)

fun CANSparkMax.follow(thisObj: CSSpark, inverted: Boolean = false) =
    follow(thisObj.motorController, inverted)

fun CANSparkMax.withPID(
    controlType: PIDControlType = PIDControlType.Velocity,
    block: CSSpark.() -> Unit = {}
) =
    CSSpark(this).apply {
        setControlType(controlType)
        block()
    }

fun WPI_TalonSRX.withPID(
    controlType: PIDControlType = PIDControlType.Velocity,
    block: WPI_TalonSRX.() -> Unit = {}
) =
    CSTalonSRX(this).apply {
        setControlType(controlType)
        block()
    }

fun TalonFX.withPID(
    controlType: PIDControlType = PIDControlType.Velocity,
    block: TalonFX.() -> Unit = {}
) =
    CSTalonFX(this).apply {
        setControlType(controlType)
        block()
    }

fun MotorController.withPID(
    pid: PIDController,
    encoder: IEncoder,
    controlType: PIDControlType = PIDControlType.Velocity,
    block: SwPIDMotorController.() -> Unit = {}
) =
    when (controlType) {
        PIDControlType.Position -> SwPIDMotorController.position(this, pid, encoder)
        PIDControlType.Velocity -> SwPIDMotorController.velocity(this, pid, encoder)
    }.apply(block)
