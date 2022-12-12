package mri.advent.y2022.day12

import mri.advent.y2022.BaseDay
import mri.advent.y2022.day12.astar.*

/** --- Day 12:  --- */
class Day12(inFile: String = "/day12.in") : BaseDay(inFile) {

    val START = 'S'
    val END = 'E'

    private fun bestPathLength(grid: Grid, start: Position, target: Position) =
        try {
            val moveCostFun: (Movement) -> Int = { movement ->
                if (grid.cellAt(movement.to).blocked) MAX_SCORE
                val diff = grid.cellAt(movement.to).type!! - grid.cellAt(movement.from).type!!
                if (diff > 1) MAX_SCORE else if (diff == 1) 1 else 5// 5 au pif. on ne distingue pas les mouvements de dénivelé nul ou négatif
            }
            aStarSearch(grid, start, target, moveCostFun).first.size - 1
        } catch (ex: Exception) {
            Int.MAX_VALUE
        }

    private fun solve(part: Int = 1): Any {
        // parse
        val lines = data.lines()

        val grid = Grid(lines.size, lines[0].length)
        lines.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                grid.cellAt(col, row).type = c
            }
        }
        val allCells = grid.allCells()
        val start = allCells.find { it.type == START }
        val end = allCells.find { it.type == END }

        if (start == null || end == null) {
            throw IllegalStateException("Invalid data")
        }
        grid.cellAt(start.pos).type = 'a'
        grid.cellAt(end.pos).type = 'z'

        val startPositions = if (part == 1) listOf(start) else allCells.filter { it.type == 'a' }
        return startPositions.minOf { bestPathLength(grid, it.pos, end.pos) }
    }

    override fun part2() = solve(part = 2)
    override fun part1() = solve(part = 1)
}

fun main() {
    Day12().execute() // 472 & 465
}
