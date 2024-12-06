package mri.advent.y2024.utils.geo2d

// generic grid
open class Grid<T>(val cells: Array<Array<T>>) {
    val width = cells[0].size
    val height = cells.size
    val allCells = cells.flatMapIndexed { row, line -> line.mapIndexed { col, _ -> Vec2D(col, row) } }
    fun cellAt(pos: Vec2D) = cells[pos.y][pos.x]
    fun setAt(position: Vec2D, value: T) {
        cells[position.y][position.x] = value
    }
    fun inGrid(pos: Vec2D) = pos.x in 0 until width && pos.y in 0 until height
    fun inGrid(line: Line) = line.points.all { inGrid(it) }
}

// Simple grid with Char cells
open class CharGrid(cells: Array<Array<Char>>) : Grid<Char>(cells) {
    constructor(cells: List<String>) : this(cells.map { it.toCharArray().toTypedArray() }.toTypedArray())

    fun setCharAt(position: Vec2D, value: Char) = setAt(position, value)
    fun charAt(position: Vec2D) = cellAt(position)
    fun lineToString(line: Line): String {
        if (!inGrid(line)) {
            throw IllegalArgumentException("Given line in not in grid")
        }
        return String(line.points.map { charAt(it) }.toCharArray())
    }
}