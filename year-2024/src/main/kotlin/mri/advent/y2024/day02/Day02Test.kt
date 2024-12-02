package mri.advent.y2024.day02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day02Test {

    private val sample = """
7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
    """.trimIndent()

    @Test
    fun testPartOne() {
        assertEquals(2, Day02(sample).partOne())
    }

    @Test
    fun testPartTwo() {
        assertEquals(4, Day02(sample).partTwo())
    }

}
