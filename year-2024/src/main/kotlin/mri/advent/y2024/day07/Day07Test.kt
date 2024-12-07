package mri.advent.y2024.day07

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day07Test {

    private val sample = """
190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20
    """.trimIndent()

    @Test
    fun testPartOne() {
        assertEquals(3749L, Day07(sample).partOne())
    }

    @Test
    fun testPartTwo() {
        assertEquals(11387L, Day07(sample).partTwo())
    }

    @Test
    fun testEquationSolvable() {
        val validEquation = Day07.Equation(190, listOf(10, 19))
        assertTrue(validEquation.solvable())

        val invalidEquation = Day07.Equation(192, listOf(17, 8, 14))
        assertFalse(invalidEquation.solvable())
    }

}
