package mri.advent.y2024.day15

import mri.advent.y2024.BaseDay
import mri.advent.y2024.utils.asChunks
import mri.advent.y2024.utils.geo2d.*

/** --- Day 15:   Warehouse Woes  https://adventofcode.com/2024/day/15  --- */
class Day15(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    class Warehouse(cells: Array<Array<Char>>, private val robotMoves: List<Direction>) : CharGrid(cells) {
        private var robotPosition = allCells.first { cellAt(it) == '@' }

        private fun moveObject(from: Vec2D, to: Vec2D) {
            setCharAt(to, charAt(from))
            setCharAt(from, '.')
        }

        private fun move(line: List<Vec2D>) {
            val tiles = line.map { cellAt(it) }
            val firstFree = tiles.indexOfFirst { it == '.' }
            val firstWall = tiles.indexOfFirst { it == '#' }
            if (firstFree < 0 || firstFree > firstWall) {
                //println(" no possible move in $tiles")
                return
            }
            println("something can move in : $tiles")
            (firstFree - 1 downTo 0).forEach {
                moveObject(line.get(it), line[it+ 1])
            }
            println(" ==>  $tiles")
            robotPosition = line[1]
        }

        private fun simulate() {
            robotMoves.forEach { move ->
                val positions = mutableListOf<Vec2D>()
                var current = robotPosition
                while (inGrid(current)) {
                    positions.add(current)
                    current = current.move(move)
                }
                move(positions)
            }
        }

        fun score() : Int {
            simulate()
            return allCells.map { it to cellAt(it) }.filter { it.second == 'O' }.sumOf { it.first.x + 100 * it.first.y }
        }
    }

    private fun parse(): Warehouse {
        val movesMapping = mapOf('^' to Direction.N, 'v' to Direction.S, '<' to Direction.W, '>' to Direction.E)
        val sections = this.data.asChunks()
        val grid = sections.first().map { it.toCharArray().toTypedArray() }.toTypedArray()
        val moves = sections.last().joinToString("").map { c -> movesMapping[c]!! }
        return Warehouse(grid, moves)
    }

    private val warehouse = parse()

    override fun partOne() = warehouse.score()
    override fun partTwo() = "TODO"
}

fun main() {
    Day15().run()
}