package mri.advent.yYYYY.dayDD

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayDDTest {

    private val sample = """
TODO
    """.trimIndent()

    @Test
    fun testPartOne() {
        assertEquals(1, DayDD(sample).partOne())
    }

    @Test
    fun testPartTwo() {
        assertEquals(1000, DayDD(sample).partTwo())
    }

}
