package mri.advent.y2024.day04

import mri.advent.y2024.BaseDay
import mri.advent.y2024.utils.geo2d.CharGrid
import mri.advent.y2024.utils.geo2d.Direction

/* --- Day 04: Ceres Search  https://adventofcode.com/2024/day/04  --- */

const val XMAS = "XMAS"
const val MAS = "MAS"

class Day04(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    val grid = CharGrid(data.lines())

    // Part 1 : find XMAS word in grid, all directions  ----------------
    fun CharGrid.findXMASWords() = allCells
        .filter { charAt(it) == XMAS[0] } // candidate starting positions
        .flatMap { start -> Direction.entries.map { start.lineFrom(it, XMAS.length) } } // all possible words from this start position
        .filter { inGrid(it) } // filter lines inside grid
        .count { lineToString(it) == XMAS } // count "XMAS" words

    override fun partOne() = grid.findXMASWords()

    // Part 2 : find X-MAS crosses -------------------
    fun String.isXmasDiagonal() = this == MAS || this == MAS.reversed()

    fun CharGrid.findMASCrosses() = allCells
        .filter { charAt(it) == 'A' }  // candidate XMAS-Cross center positions
        .filter { it.x in (1 until width - 1) && it.y in (1 until height - 1) } // exclude 'A' on grid border lines
        .map { center ->  // creates pair of diagonals from this center position
            Pair(
                lineToString(center.move(Direction.NW).lineFrom(Direction.SE, MAS.length)),
                lineToString(center.move(Direction.SW).lineFrom(Direction.NE, MAS.length))
            )
        }.count { diagonalesPair -> diagonalesPair.first.isXmasDiagonal() && diagonalesPair.second.isXmasDiagonal() }

    override fun partTwo() = grid.findMASCrosses()
}

fun main() {
    Day04().run()
}
