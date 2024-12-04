package mri.advent.y2024.day04

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day04Test {

    private val sample = """
MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX
    """.trimIndent()

    @Test
    fun testPartOne() {
        assertEquals(18, Day04(sample).partOne())
    }

    @Test
    fun testPartTwo() {
        assertEquals(9, Day04(sample).partTwo())
    }

}
