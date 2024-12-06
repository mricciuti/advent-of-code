package mri.advent.y2024.day06

import mri.advent.y2024.BaseDay
import mri.advent.y2024.utils.geo2d.CharGrid
import mri.advent.y2024.utils.geo2d.Direction
import mri.advent.y2024.utils.geo2d.Vec2D

/* --- Day 06:  Guard Gallivant (https://adventofcode.com/2024/day/06)  --- */
class Day06(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    // move in given map and stop when out of map area; returns all visited cells, including start position
    fun visit(map: CharGrid): Set<Vec2D> {
        val visited = mutableSetOf<Vec2D>()

        var position = map.allCells.first { map.cellAt(it) == '^' }
        var direction = Direction.N

        while (true) {
            visited.add(position)
            val nextPosition = position.move(direction)
            if (!map.inGrid(nextPosition)) {
                break // out of area
            }
            val nextCell = map.cellAt(nextPosition)
            if (nextCell == '#') {
                direction = Direction.nextCardinal(direction)
            } else {
                position = nextPosition
            }
        }
        return visited
    }

    override fun partOne() = visit(CharGrid(data.lines())).size

    // test if guard would get stuck in loop in given map and candidate obstacle
    fun guardStuckInLoop(map: CharGrid, start: Vec2D, obstaclePosition: Vec2D): Boolean {
        map.setCharAt(obstaclePosition, '#') // patch map with candidate obstacle
        var position = start
        var direction = Direction.N
        val visited = mutableSetOf<Pair<Vec2D, Direction>>()
        var loopDetected = false
        while (true) {
            var posWithDirection = Pair(position, direction)
            if (visited.contains(posWithDirection)) {
                loopDetected = true  // guard stuck in loop !
                break
            }
            visited.add(posWithDirection)
            val nextPosition = position.move(direction)
            if (!map.inGrid(nextPosition)) {
                break // guard escaped :(
            }
            val nextCell = map.cellAt(nextPosition)
            if (nextCell == '#') {
                direction = Direction.nextCardinal(direction)
            } else {
                position = nextPosition
            }
        }
        map.setCharAt(obstaclePosition, '.')  // revert change
        return loopDetected
    }

    override fun partTwo(): Any {
        val map = CharGrid(data.lines())
        val start = map.allCells.first { map.cellAt(it) == '^' }

        // get initial guard path
        val guardPath = visit(map)
        // test all candidate positions to put an obstacle (visited path except start position)
        return guardPath.minus(start).count { position ->  guardStuckInLoop(map, start, position)}
    }
}

fun main() {
    Day06().run()
}