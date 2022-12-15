package mri.advent.y2022.day14

import com.github.ajalt.mordant.TermColors
import mri.advent.y2022.BaseDay

/** --- Day 14:  --- */
class Day14(inFile: String = "/day14.in") : BaseDay(inFile, debug = false) {

    val SAND_START = Vec2D(500, 0)
    val ANIMATION = false

    enum class DIRECTION(val delta: Vec2D) { DOWN(Vec2D(0, 1)), LEFT(Vec2D(-1, 1)), RIGHT(Vec2D(1, 1)) }

    data class Cell(val coord: Vec2D, var rock: Boolean = false, var sand: Boolean = false) {
        fun blocked() = rock ||  sand
        override fun toString() = if (sand) "O" else if (blocked()) "#" else "."
    }

    data class Grid(val cells: Array<Array<Cell>>) {
        private val startLeft = cells[0][0].coord.x
        private val xRange = startLeft until startLeft + cells[0].size
        private val yRange = cells.indices
        fun block(point: Vec2D) {
            cellAt(point).rock = true
        }

        fun isBlocked(point: Vec2D) = inGrid(point) && cellAt(point).blocked()
        fun inGrid(point: Vec2D) = point.x in xRange && point.y in yRange
        fun cellAt(position: Vec2D) = cells[position.y][position.x - startLeft]
        override fun toString() = cells.joinToString("\n") { it.joinToString("") { it.toString() } }
    }

    data class Vec2D(val x: Int, val y: Int) {
        fun moveTo(direction: Vec2D) = Vec2D(this.x + direction.x, this.y + direction.y)
        fun pointsTo(other: Vec2D): List<Vec2D> {
            return (minOf(x, other.x)..maxOf(x, other.x))
                .flatMap { x -> (minOf(y, other.y)..maxOf(y, other.y)).map { y -> Vec2D(x, y) } }
        }
    }

    data class Line(val start: Vec2D, val end: Vec2D) {
        val points = start.pointsTo(end)
    }

    fun String.toPoint(): Vec2D = with(this.split(",")) { return Vec2D(this[0].toInt(), this[1].toInt()) }
    fun String.toLines() = this.split(" -> ")
        .zipWithNext()
        .map { it.first.toPoint() to it.second.toPoint() }
        .map { Line(it.first, it.second) }

    private fun solve(part: Int = 1): Any {
        val allPoints = data.lines().flatMap { it.toLines() }.flatMap { it.points }.toMutableList()
        var (left, right) = allPoints.minOf { it.x } to allPoints.maxOf { it.x }
        var bottom = allPoints.maxOf { it.y }

        // Part 2 : assume the floor is an infinite horizontal line
        // with a y coordinate equal to two plus the highest y coordinate of any point in your scan.
        if (part == 2) {
            left -= bottom
            right += bottom
            bottom += 2
            (left..right).forEach { allPoints.add(Vec2D(it, bottom)) }
        }
        val grid = Grid(Array(bottom + 1) { y -> Array(right - left + 1) { x -> Cell(Vec2D(x + left, y)) } })
        allPoints.forEach { grid.block(it) }

        var sandUnits = 0

        println(grid)
        Thread.sleep(5000)
        // main loop
        var gameOver = false
        while (!gameOver) {

            // sand unit loop
            var sandPosition = SAND_START
            if (grid.isBlocked(SAND_START)) {
                gameOver = true
                debug("sandUnits $sandUnits : start position is blocked.")
            } else {
                while (true) {
                    if (ANIMATION) {
                        Thread.sleep(50)
                        println(grid)
                    }

                    val next = DIRECTION.values()
                        .map { sandPosition.moveTo(it.delta) }
                        .firstOrNull { !grid.isBlocked(it) }
                    if (next == null) {
                        debug("sandUnits $sandUnits is blocked")
                        sandUnits++
                        grid.block(sandPosition)
                        break
                    }
                    grid.cellAt(sandPosition).sand = false
                    sandPosition = next
                    debug("sandUnits $sandUnits moves to next $next")
                    if (!grid.inGrid(sandPosition)) {
                        debug("sandUnits $sandUnits is OUT $sandPosition")
                        gameOver = true
                        break
                    }
                    grid.cellAt(sandPosition).sand = true
                }
            }
        }
        return sandUnits
    }

    override fun part2() = solve(2) // 27539,   600ms
    override fun part1() = solve()       // 838 , 100ms


}

fun main() {
    Day14().execute()
}