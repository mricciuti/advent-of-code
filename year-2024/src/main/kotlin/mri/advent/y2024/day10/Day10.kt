package mri.advent.y2024.day10

import mri.advent.y2024.BaseDay
import mri.advent.y2024.utils.geo2d.*

/**  ---  Day 10 :    [Hoof It](https://adventofcode.com/2024/day/10)  --- */
class Day10(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    class TopographicMap(heights: Array<Array<Int>>) : Grid<Int>(heights) {
        constructor(lines: List<String>) : this(lines.map { it.toHeights() }.toTypedArray())

        val validMovePredicate: ((Vec2D, Vec2D) -> Boolean) = { from, to -> cellAt(to) == cellAt(from) + 1 }
        val pathCompletedPredicate: ((Path) -> Boolean) = { path -> cellAt(path.end()) == 9 }

        // find & store all possible trails from all available trailhead positions
        val hikingTrails = this.allCells
            .filter { cellAt(it) == 0 } // trailhead
            .flatMap { trailHead ->
                findAllPaths(trailHead, pathCompletedPredicate, validMovePredicate)
            }
    }

    // parse/init map
    val map = TopographicMap(data.lines())

    override fun partOne() = map.hikingTrails.distinctBy { it.start() to it.end() }.size

    override fun partTwo() = map.hikingTrails.size
}

// helpers
fun String.toHeights() = this.map { c -> if (c.isDigit()) c.digitToInt() else -1 }.toTypedArray()

fun main() {
    Day10().run()
}