package mri.advent.yYYYY.dayDD

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayDDTest {

    val sample = """
TODO     
    """.trimIndent()

    @Test
    fun `test part one`() {
        assertEquals(1, DayDD(sample).partOne())
    }

    @Test
    fun `test part two`() {
        assertEquals(1000, DayDD(sample).partTwo())
    }

}
