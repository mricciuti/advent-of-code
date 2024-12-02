package mri.advent.y2024.day02

import mri.advent.y2024.BaseDay
import mri.advent.y2024.utils.toInts
import kotlin.math.abs

/** --- Day 02: Red-Nosed Reports  --- `https://adventofcode.com/2024/day/02`  */
data class Report(val levels: List<Int>) {

    private fun safeLevels(levels: List<Int>): Boolean {
        val deltas = levels.zipWithNext { a, b -> b - a }
        return deltas.all { abs(it) in 1..3 } && (deltas.all { it > 0 } || deltas.all { it < 0 })
    }

    fun isSafe(tolerateOneBadLevel: Boolean = false): Boolean {
        if (safeLevels(levels)) return true
        if (tolerateOneBadLevel) {
            // try removing one level
            repeat(levels.size) { index ->
                if (safeLevels(levels.toMutableList().also { it.removeAt(index) })) return true
            }
        }
        return false
    }
}

class Day02(dataOrNull: String? = null) : BaseDay(dataOrNull) {
    private val reports = data.lines().map { Report(it.toInts()) }

    override fun partOne() = reports.count { it.isSafe() }
    override fun partTwo() = reports.count { it.isSafe(tolerateOneBadLevel = true) }
}

fun main() {
    Day02().run()
}