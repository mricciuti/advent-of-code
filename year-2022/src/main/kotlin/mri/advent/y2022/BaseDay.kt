package mri.advent.y2022

import kotlin.system.measureTimeMillis

abstract class BaseDay(inFile: String, val debug: Boolean = false) {

    protected val data = resourceAsString(inFile)

    abstract fun part1(): Any
    abstract fun part2(): Any

    fun execute() {
        with(measureTimeMillis { println("Part 1: " + part1()) }) {
            println("  => time taken: $this ms")
        }
        with(measureTimeMillis { println("Part 2: " + part2()) }) {
            println("  => time taken: $this ms")
        }
    }

    fun debug(msg: Any) {
        if (debug) println(msg)
    }

    private fun resourceAsString(resource: String): String {
        getClassResource(resource)
            ?.let { return it.readText() }
            ?: throw IllegalArgumentException("Resource not found: $resource")
    }

    private fun getClassResource(resource: String) = javaClass.getResource("/" + javaClass.packageName.replace(".", "/") + resource)
}