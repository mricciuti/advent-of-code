package mri.advent.y2024.utils.geo2d

/**
 * Utility classes for GEO 2D:  points, line, directions, etc...
 */

data class Vec2D(val x: Int, val y: Int) {
    fun move(direction: Direction, steps: Int = 1) = Vec2D(x + direction.delta.x * steps, y + direction.delta.y * steps)

    // create a Line starting from this position with given direction and length
    fun lineFrom(direction: Direction, length: Int) = Line(Array(length) { this.move(direction, it) }.toList())
}

data class Line(val points: List<Vec2D>)

enum class Direction(val delta: Vec2D) {
    N(Vec2D(0, -1)),
    E(Vec2D(1, 0)),
    S(Vec2D(0, 1)),
    W(Vec2D(-1, 0)),
    NE(Vec2D(1, -1)),
    SE(Vec2D(1, 1)),
    SW(Vec2D(-1, 1)),
    NW(Vec2D(-1, -1))
}