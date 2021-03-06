package com.chopshop166.chopshoplib

import edu.wpi.cscore.VideoSink
import edu.wpi.cscore.VideoSource
import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.wpilibj.Sendable
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard

class DashboardWrapper {
    operator fun set(name : String, value : Sendable) = SmartDashboard.putData(name, value)
    operator fun set(name : String, value : Boolean) = SmartDashboard.putBoolean(name, value)
    operator fun set(name : String, value : String) = SmartDashboard.putString(name, value)
    operator fun set(name : String, value : Double) = SmartDashboard.putNumber(name, value)
    operator fun get(name : String) = SmartDashboard.getData(name)
    fun entry(name : String) = SmartDashboard.getEntry(name)
    operator fun invoke(block : DashboardWrapper.() -> Unit = {}) = apply(block)
}

val dashboard = DashboardWrapper()

class ShuffleboardWrapper {
    operator fun get(name : String) = Shuffleboard.getTab(name)
}

val shuffleboard = ShuffleboardWrapper()

class ChooserWrapper<T>(val chooser : SendableChooser<T>) {
    infix fun String.default(value : T) { chooser.setDefaultOption(this, value) }
    infix fun String.chooses(value : T) { chooser.addOption(this, value) }
}

fun <T> chooser(block : ChooserWrapper<T>.() -> Unit) = SendableChooser<T>().apply {
    ChooserWrapper<T>(this).block()
}

class VideoSourceWrapper(val source : VideoSource) {
    operator fun get(name : String) = source.getProperty(name).get()
    operator fun set(name : String, value : Int) = source.getProperty(name).set(value)
}

class VideoSinkWrapper(val sink : VideoSink) {
    operator fun get(name : String) = sink.getProperty(name).get()
    operator fun set(name : String, value : Int) = sink.getProperty(name).set(value)
}

class CameraServerWrapper(private val base : CameraServer) {
    fun server(block : VideoSinkWrapper.() -> Unit = {}) =
        base.getServer().apply { VideoSinkWrapper(this).block() }
    fun server(name : String, block : VideoSinkWrapper.() -> Unit = {}) =
        base.getServer(name).apply { VideoSinkWrapper(this).block() }
    fun source(block : VideoSourceWrapper.() -> Unit = {}) =
        base.startAutomaticCapture().apply { VideoSourceWrapper(this).block() }
    fun source(dev : Int, block : VideoSourceWrapper.() -> Unit = {}) =
        base.startAutomaticCapture(dev).apply { VideoSourceWrapper(this).block() }
    fun source(camera : VideoSource, block : VideoSinkWrapper.() -> Unit = {}) =
        base.startAutomaticCapture(camera).apply { VideoSinkWrapper(this).block() }
    fun source(name : String, dev : Int, block : VideoSourceWrapper.() -> Unit = {}) =
        base.startAutomaticCapture(name, dev).apply { VideoSourceWrapper(this).block() }
    fun source(name : String, path : String, block : VideoSourceWrapper.() -> Unit = {}) =
        base.startAutomaticCapture(name, path).apply { VideoSourceWrapper(this).block() }
}

val cameraServer = CameraServerWrapper(CameraServer.getInstance())
