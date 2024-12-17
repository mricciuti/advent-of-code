package mri.advent.y2024.day16

import mri.advent.y2024.BaseDay
import mri.advent.y2024.utils.geo2d.*

/** --- Day 16:  https://adventofcode.com/2024/day/16  --- */
class Day16(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    data class PosAndDir(val pos: Vec2D, val dir: Direction) {
        fun move(move: Move) = when (move) {
            Move.FORWARD -> PosAndDir(pos.move(dir), dir)
            Move.TURN_LEFT -> PosAndDir(pos, Direction.nextCardinal(dir, clockwise = false))
            Move.TURN_RIGHT -> PosAndDir(pos, Direction.nextCardinal(dir, clockwise = true))
        }

        override fun toString() = "$pos ($dir)"
    }

    enum class Move(val score: Int) {
        FORWARD(1),
        TURN_LEFT(1000),
        TURN_RIGHT(1000)
    }

    data class Path(val posAndDirections: MutableList<PosAndDir> = mutableListOf(), val score: Int = 0) {
        override fun toString() = "score: $score , steps: ${posAndDirections.joinToString("  | ") { it.toString() }}"
        fun positions() = posAndDirections.map { it.pos }.toSet()
    }

    class Maze(lines: List<String>) : CharGrid(lines) {
        val startPosition = allCells.first { this.cellAt(it) == 'S' }
        val endPosition = allCells.first { this.cellAt(it) == 'E' }

        fun validPosition(position: Vec2D) = inGrid(position) && cellAt(position) != '#'

        fun Path.tryExpand(move: Move): Path? {
            val next = posAndDirections.last().move(move)
            if (validPosition(next.pos) && !posAndDirections.contains(next)) {
                return Path(posAndDirections.toMutableList().apply { add(next) }, score + move.score)
            }
            return null
        }

        fun findBestPaths(): List<Path> {
            val paths = mutableListOf<Path>()
            val minCosts = mutableMapOf<PosAndDir, Int>()
            val start = PosAndDir(startPosition, Direction.E)
            val queue = ArrayDeque<Path>().apply { add(Path(mutableListOf(start), 0)) }

            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()
                if (current.posAndDirections.last().pos == endPosition) {
                    paths.add(current)
                    continue
                }
                // better path already exists ?
                if ((minCosts[current.posAndDirections.last()] ?: Int.MAX_VALUE) < current.score) {
                    continue
                }

                minCosts[current.posAndDirections.last()] = current.score
                // try expand
                Move.entries.forEach { move ->
                    current.tryExpand(move)?.let { path ->
                        queue.add(path)
                    }

                }
            }
            val minScore = paths.minOf { it.score }
            return paths.filter { it.score == minScore }
        }
    }

    val maze = Maze(data.lines())

    override fun partOne() = maze.findBestPaths().minOf { it.score }
    override fun partTwo() = maze.findBestPaths().flatMap { it.positions() }.toSet().size
}

fun main() {
    Day16().run()
}