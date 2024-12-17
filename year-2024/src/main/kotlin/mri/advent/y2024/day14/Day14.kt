package mri.advent.y2024.day14

import mri.advent.y2024.BaseDay
import mri.advent.y2024.utils.geo2d.CharGrid
import mri.advent.y2024.utils.geo2d.Vec2D

/** --- [Day 14](https://adventofcode.com/2024/day/14):  Restroom Redoubt   --- */
data class Robot(var position: Vec2D, val velocity: Vec2D)

val robotRegex = """(-?\d+),(-?\d+)""".toRegex()
fun String.toRobot(): Robot {
    val intPairs = robotRegex.findAll(this).map { matchResult ->
        val (x, y) = matchResult.destructured
        Vec2D(x.toInt(), y.toInt())
    }
    return Robot(intPairs.first(), intPairs.last())
}

class Day14(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    data class Quadran(val xRange: IntRange, val yRange: IntRange) {
        fun contains(position: Vec2D) = position.x in xRange && position.y in yRange
    }

    class RobotsGrid(data: Array<Array<Char>>, private val robots: List<Robot>) : CharGrid(data) {

        private val quadrans = buildList {
            add(Quadran(IntRange(0, width / 2 - 1), IntRange(0, height / 2 - 1))) // NW
            add(Quadran(IntRange(0, width / 2 - 1), IntRange(height / 2 + 1, height - 1))) // SW
            add(Quadran(IntRange(width / 2 + 1, width - 1), IntRange(0, height / 2 - 1))) // NE
            add(Quadran(IntRange(width / 2 + 1, width - 1), IntRange(height / 2 + 1, height - 1))) // SE
        }

        // teleportation when needed
        private fun adjustPosition(position: Vec2D): Vec2D {
            val x = if (position.x < 0) this.width + position.x else if (position.x >= width) position.x - width else position.x
            val y = if (position.y < 0) this.height + position.y else if (position.y >= height) position.y - height else position.y
            return Vec2D(x, y)
        }

        private fun quadranFor(position: Vec2D) = quadrans.firstOrNull { it.contains(position) }


        private fun detectTree(): Boolean {
            // create image from grid
            allCells.forEach { setCharAt(it, '.') }
            robots.forEach { setCharAt(it.position, '0') }
            return cells.map { String(it.toCharArray()) }.count { it.contains("00000") } > 5
        }

        fun solve(simulationSteps: Int, part2: Boolean = false): Int {
            // move bots
            repeat(simulationSteps) { step ->
                robots.forEach { it.position = adjustPosition(it.position.plus(it.velocity)) }
                if (part2 && detectTree()) {
                    println("found tree after  ${step + 1} seconds!!  ")
                    println(this)
                    return step + 1
                }
            }
            // calculate quadrans
            val robotsPerQuadran = robots.groupBy { quadranFor(it.position) }
            val quadranSize = robotsPerQuadran.filter { it.key != null }.values.map { it.size }
            return quadranSize.fold(1) { acc, i -> acc * i }
        }

        override fun toString(): String {
            return this.cells.joinToString("\n") { it.joinToString("") }
        }
    }

    private fun parse(): RobotsGrid {
        val robots = data.lines().map { it.toRobot() }
        val xRange = IntRange(robots.minOf { it.position.x }, robots.maxOf { it.position.x })
        val yRange = IntRange(robots.minOf { it.position.y }, robots.maxOf { it.position.y })
        val tiles = Array(yRange.count()) { Array(xRange.count()) { '.' } }
        return RobotsGrid(tiles, robots)
    }

    override fun partOne() = parse().solve(100)
    override fun partTwo() = parse().solve(20000, true)
}

fun main() {
    Day14().run()
}