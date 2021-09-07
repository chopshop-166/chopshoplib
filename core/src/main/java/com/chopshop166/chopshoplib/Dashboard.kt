@file:OptIn(kotlin.experimental.ExperimentalTypeInference::class)
package com.chopshop166.chopshoplib

import edu.wpi.cscore.VideoSink
import edu.wpi.cscore.VideoSource
import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.wpilibj.Sendable
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard

class DashboardWrapper internal constructor () {
    operator fun set(name: String, value: Sendable) = (name displays value)
    operator fun set(name: String, value: Boolean) = (name displays value)
    operator fun set(name: String, value: String) = (name displays value)
    operator fun set(name: String, value: Double) = (name displays value)
    operator fun set(name: String, value: Int) = (name displays value)

    operator fun get(name: String) = SmartDashboard.getData(name)

    operator fun invoke(block: DashboardWrapper.() -> Unit = {}) = apply(block)

    infix fun String.displays(value: Sendable) = SmartDashboard.putData(this, value)
    infix fun String.displays(value: Boolean) = SmartDashboard.putBoolean(this, value)
    infix fun String.displays(value: String) = SmartDashboard.putString(this, value)
    infix fun String.displays(value: Double) = SmartDashboard.putNumber(this, value)
    infix fun String.displays(value: Int) = SmartDashboard.putNumber(this, value.toDouble())

    fun entry(name: String) = SmartDashboard.getEntry(name)
}
val dashboard = DashboardWrapper()

class ShuffleboardContainerWrapper internal constructor (val con : ShuffleboardContainer) {

    val title : String get() { return con.getTitle() }

    operator fun set(name: String, value: Sendable) = name(value)
    @OverloadResolutionByLambdaReturnType
    @JvmName("setBoolean") operator fun set(name: String, value: () -> Boolean) = name(value)
    @JvmName("setString") operator fun set(name: String, value: () -> String) = name(value)
    @JvmName("setNumber") operator fun set(name: String, value: () -> Double) = name(value)
    @JvmName("setInt") operator fun set(name: String, value: () -> Int) = name(value)
    
    operator fun get(name: String) = ShuffleboardContainerWrapper(con.getLayout(name))
    fun layout(name : String, block: ShuffleboardContainerWrapper.() -> Unit = {}) = this[name].run(block)

    operator fun String.invoke(value: Sendable) = con.add(this, value)
    @OverloadResolutionByLambdaReturnType
    @JvmName("invokeBoolean") operator fun String.invoke(value: () -> Boolean) = con.addBoolean(this, value)
    @JvmName("invokeString") operator fun String.invoke(value: () -> String) = con.addString(this, value)
    @JvmName("invokeNumber") operator fun String.invoke(value: () -> Double) = con.addNumber(this, value)
    @JvmName("invokeInt") operator fun String.invoke(value: () -> Int) = con.addNumber(this) { value().toDouble() }
}
fun shuffleboard(name : String, block: ShuffleboardContainerWrapper.() -> Unit = {}) = ShuffleboardContainerWrapper(Shuffleboard.getTab(name)).apply(block)

class ChooserWrapper<T>(val chooser: SendableChooser<T>) {
    infix fun String.default(value: T) { chooser.setDefaultOption(this, value) }
    infix fun String.chooses(value: T) { chooser.addOption(this, value) }
}

inline fun <reified T> chooser(block: ChooserWrapper<T>.() -> Unit) = SendableChooser<T>().apply {
    ChooserWrapper<T>(this).block()
}

class VideoSourceWrapper(val source: VideoSource) {
    operator fun get(name: String) = source.getProperty(name).get()
    operator fun set(name: String, value: Int) = source.getProperty(name).set(value)
}

class VideoSinkWrapper(val sink: VideoSink) {
    operator fun get(name: String) = sink.getProperty(name).get()
    operator fun set(name: String, value: Int) = sink.getProperty(name).set(value)
}

class CameraServerWrapper(private val base: CameraServer) {
    fun server(block: VideoSinkWrapper.() -> Unit = {}) =
        base.getServer().apply { VideoSinkWrapper(this).block() }
    fun server(name: String, block: VideoSinkWrapper.() -> Unit = {}) =
        base.getServer(name).apply { VideoSinkWrapper(this).block() }
    fun source(block: VideoSourceWrapper.() -> Unit = {}) =
        base.startAutomaticCapture().apply { VideoSourceWrapper(this).block() }
    fun source(dev: Int, block: VideoSourceWrapper.() -> Unit = {}) =
        base.startAutomaticCapture(dev).apply { VideoSourceWrapper(this).block() }
    fun source(camera: VideoSource, block: VideoSinkWrapper.() -> Unit = {}) =
        base.startAutomaticCapture(camera).apply { VideoSinkWrapper(this).block() }
    fun source(name: String, dev: Int, block: VideoSourceWrapper.() -> Unit = {}) =
        base.startAutomaticCapture(name, dev).apply { VideoSourceWrapper(this).block() }
    fun source(name: String, path: String, block: VideoSourceWrapper.() -> Unit = {}) =
        base.startAutomaticCapture(name, path).apply { VideoSourceWrapper(this).block() }
}

val cameraServer = CameraServerWrapper(CameraServer.getInstance())
