package mri.advent.y2024.day01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01Test {

    private val sample = """
3   4
4   3
2   5
1   3
3   9
3   3        
    """.trimIndent()

    @Test
    fun testPartOne() {
        assertEquals(11, Day01(sample).partOne())
    }

    @Test
    fun testPartTwo() {
        assertEquals(31, Day01(sample).partTwo())
    }

}
