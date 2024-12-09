package mri.advent.y2024.day08

import mri.advent.y2024.BaseDay
import mri.advent.y2024.utils.geo2d.CharGrid
import mri.advent.y2024.utils.geo2d.Vec2D
import mri.advent.y2024.utils.pairs

/** --- Day 08: Resonant Collinearity  https://adventofcode.com/2024/day/08  --- */
class Day08(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    class City(map: List<String>) : CharGrid(map) {

        // create sequence of possible node positions from given start (included) , moving delta, until out of grid bound
        private fun antinodePositions(start: Vec2D, delta: Vec2D): List<Vec2D> {
            val positions = mutableListOf<Vec2D>(start)
            var next = start.plus(delta)
            while(inGrid(next)) {
                positions.add(next)
                next = next.plus(delta)
            }
            return positions
        }

        fun detectAntinodes(part2: Boolean = false): Set<Vec2D> {
            val antinodes = mutableSetOf<Vec2D>()
            allCells.filter { this.cellAt(it) != '.' && this.cellAt(it) != '#' }
                .groupBy { this.cellAt(it) }
                .forEach { antennasForFrequency ->
                    //println("process frequency: ${antennasForFrequency.key}, antennas size: ${antennasForFrequency.value.size}")
                    antennasForFrequency.value.pairs().forEach { (first, second) ->
                        val delta = second.minus(first)
                        if (part2) {
                            antinodes.addAll(antinodePositions(second, delta))
                            antinodes.addAll(antinodePositions(first, delta.opposite()))
                        } else {
                            antinodes.add(first.minus(delta))
                            antinodes.add(second.plus(delta))
                        }
                    }
                }
//            antinodes.forEach { if (inGrid(it) && cellAt(it ) == '.') this.setCharAt(it, '#') }
//            println(this)
            return antinodes.filter { this.inGrid(it) }.toSet()
        }
    }

    override fun partOne() = City(data.lines()).detectAntinodes().size
    override fun partTwo() = City(data.lines()).detectAntinodes(true).size
}

fun main() {
    Day08().run()
}