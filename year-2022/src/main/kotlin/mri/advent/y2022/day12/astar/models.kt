package mri.advent.y2022.day12.astar

import kotlin.math.*

// Available directions for movements in Grid
enum class Direction(val x: Int, val y: Int) { UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0); }

// A position in grid - 0,0 is at upper left corner
data class Position(val x: Int, val y: Int) {
    fun to(direction: Direction) = Position(x + direction.x, y + direction.y)
    fun distance(target: Position): Int {
        val dx = abs(target.x - x)
        val dy = abs(target.y - y)
        //  manhattan distance
        return dx + dy
    }
}
// one step movement from a position in grid to another position
data class Movement(val from: Position, val to: Position)

// A cell in a Grid.  has a position and a type (content)
data class Cell(val pos: Position) {
    constructor(x: Int, y: Int) : this(Position(x, y))

    var type: Char? = null
    var blocked = false
}

class Grid(val height: Int, val width: Int, private var validDirections: Array<Direction> = Direction.values()) {

    // the grid cells
    private val cells: Array<Array<Cell>> = Array(height) { row -> Array(width) { col -> Cell(col, row) } }

    // functions for locating cells in grid
    private fun inGrid(x: Int, y: Int) = x in 0 until width && y in 0 until height
    fun inGrid(pos: Position) = inGrid(pos.x, pos.y)
    fun cellAt(x: Int, y: Int) =
        if (inGrid(x, y)) cells[y][x] else throw IllegalArgumentException("Coords out of range $x,$y")

    fun cellAt(pos: Position) = cellAt(pos.x, pos.y)
    fun allCells() = cells.toList().flatMap { it.toList() }.toList()

    fun neighbours(pos: Position): List<Position> {
        return validDirections.map { pos.to(it) }.filter { inGrid(it) }.map { Position(it.x, it.y) }
    }

}