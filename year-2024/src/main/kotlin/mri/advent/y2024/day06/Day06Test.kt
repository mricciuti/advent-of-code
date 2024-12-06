package mri.advent.y2024.day06

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day06Test {

    private val sample = """
....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...
    """.trimIndent()

    @Test
    fun testPartOne() {
        assertEquals(41, Day06(sample).partOne())
    }

    @Test
    fun testPartTwo() {
        assertEquals(6, Day06(sample).partTwo())
    }

}
