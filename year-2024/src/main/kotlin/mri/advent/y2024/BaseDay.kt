package mri.advent.y2024

import kotlin.reflect.KClass
import kotlin.system.measureTimeMillis

abstract class BaseDay(_data: String?) {
    protected val data = _data ?: dayInputData(this::class)

    abstract fun partOne(): Any
    abstract fun partTwo(): Any

    fun run() {
        with(measureTimeMillis { println("Part 1: " + partOne()) }) {
            println("  => time taken: $this ms")
        }
        with(measureTimeMillis { println("Part 2: " + partTwo()) }) {
            println("  => time taken: $this ms")
        }
    }
}

private fun <T : BaseDay> dayInputData(dayClazz: KClass<T>) = BaseDay::class.java.getResource("/input/${dayClazz.simpleName!!.lowercase()}.txt")?.readText()
    ?: throw IllegalArgumentException("Resource not found for day : $dayClazz")