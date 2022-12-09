package mri.advent.y2022.day09

import mri.advent.y2022.BaseDay
import kotlin.math.*

/** --- Day 09:  --- */
class Day09(inFile: String = "/day09.in") : BaseDay(inFile) {

    data class Vec2D(val x: Int, val y: Int) {
        fun moveTo(direction: Direction): Vec2D {
            return when (direction) {
                Direction.UP -> Vec2D(x, y - 1)
                Direction.DOWN -> Vec2D(x, y + 1)
                Direction.LEFT -> Vec2D(x - 1, y)
                Direction.RIGHT -> Vec2D(x + 1, y)
            }
        }

        fun moveTowards(target: Vec2D): Vec2D {
            val delta = Vec2D(target.x - this.x, target.y - this.y)
            return Vec2D(
                this.x + if (delta.x < 0) -1 else if (delta.x > 0) 1 else 0,
                this.y + if (delta.y < 0) -1 else if (delta.y > 0) 1 else 0
            )
        }

        fun isAdjacentTo(other: Vec2D): Boolean {
            return abs(this.x - other.x) <= 1 && abs(this.y - other.y) <= 1
        }
    }

    enum class Direction(val asString: String) { UP("U"), RIGHT("R"), DOWN("D"), LEFT("L"); }

    data class Move(val direction: Direction, val steps: Int)

    fun parseMoves() = data.lines().map { line ->
        Move(Direction.values().first { it.asString == line.substringBefore(" ") }, line.substringAfter(" ").toInt())
    }

    override fun part1(): Any {
        var headPosition = Vec2D(0, 0)
        var tailPos = Vec2D(0, 0)
        val tailPositions = mutableListOf(tailPos)

        parseMoves().forEach { move ->
            repeat(move.steps) {
                headPosition = headPosition.moveTo(move.direction)
                if (!tailPos.isAdjacentTo(headPosition)) {
                    tailPos = tailPos.moveTowards(headPosition)
                    tailPositions.add(tailPos)
                }
            }
        }
        return tailPositions.distinct().size
    }

    override fun part2(): Any {
        val knots = Array(10) { Vec2D(0, 0) }
        val tailPositions = mutableListOf(knots[9])
        parseMoves()
            .forEach { move ->
                repeat(move.steps) {
                    // knots[0] : head ->  knots[9]: tail

                    knots[0] = knots[0].moveTo(move.direction)
                    // move following knots
                    (1 until knots.size).forEach { idx ->
                        val currIdx = idx
                        val prevIdx = idx - 1
                        if (!knots[currIdx].isAdjacentTo(knots[prevIdx])) {
                            // need to move
                            knots[currIdx] = knots[currIdx].moveTowards(knots[prevIdx])
                        }
                        if (currIdx == knots.size - 1) {
                            tailPositions.add(knots[currIdx])
                        }
                    }
                }
            }
        return tailPositions.distinct().size
    }

}

fun main() {
    Day09().execute()
}
