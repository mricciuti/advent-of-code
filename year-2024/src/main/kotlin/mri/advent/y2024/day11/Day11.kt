package mri.advent.y2024.day11

import mri.advent.y2024.BaseDay
import mri.advent.y2024.utils.*

/** --- Day 11: Plutonian Pebbles https://adventofcode.com/2024/day/11  --- */

// Stone transformation process
fun Long.transform(): List<Long> {
    if (this.equals(0L)) return listOf(1L)
    with(this.toString()) {
        if (this.length.isEven()) return this.splitHalf().map { it.toLong() }
    }
    return listOf(this * 2024L)
}

// stone transformation cache
val transformationCache = mutableMapOf<Long, List<Long>>()
fun Long.transformUsingCache() = transformationCache.getOrPut(this) { this.transform() }

fun List<Long>.transform() = this.flatMap { it.transformUsingCache() }

class Day11(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    override fun partOne(): Long {
        var stones = data.lines().first().toLongs()
        repeat(25) {
            stones = stones.transform()
        }
        return stones.size.toLong()
    }

    override fun partTwo(): Long {
        var stonesMap = data.lines().first().toLongs().associateWith { 1L }
        val start = System.currentTimeMillis()
        repeat(75) {
            //println("iteration ${it + 1} (${System.currentTimeMillis() - start} ms)  - cache size: ${transformationCache.size}  stones count: ${stonesMap.values.sum()}")
            val transformedStones = mutableMapOf<Long, Long>()
            stonesMap.entries.forEach { valueToCount ->
                // stone  S  transformed in  [S1]  or [S1, S2] (if split occured) => increase counter for S1 (& S2) in result map
                valueToCount.key.transformUsingCache().forEach { transformed ->
                    transformedStones.put(transformed, transformedStones.getOrDefault(transformed, 0) + valueToCount.value)
                }
            }
            stonesMap = transformedStones
        }
        return stonesMap.values.sum()
    }
}

fun main() {
    Day11().run()
}