package com.chopshop166.chopshoplib

import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.withSign

fun Double.clamped(minVal : Double, maxVal : Double) = max(minVal, min(maxVal, this))
fun Float.clamped(minVal : Float, maxVal : Float) = max(minVal, min(maxVal, this))
fun Int.clamped(minVal : Int, maxVal : Int) = max(minVal, min(maxVal, this))
fun Long.clamped(minVal : Long, maxVal : Long) = max(minVal, min(maxVal, this))

fun Double.signPow(exp : Double) = pow(exp).withSign(this)

typealias BoolFunc = ()->Boolean
operator fun BoolFunc.not() = {!this()}
infix fun BoolFunc.and(other : BoolFunc) = {this() && other()}
infix fun BoolFunc.or(other : BoolFunc) = {this() || other()}
