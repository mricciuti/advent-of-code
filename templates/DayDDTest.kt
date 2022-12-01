package mri.advent.yYYYY.dayDD

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * --- Day DD Test:  ---
 */
class DayDDTest {

    val sample = "/dayDD_sample.in"

    @Test
    fun `test part1`() {
        assertEquals(7, DayDD(sample).part1())
    }

    @Test
    fun `test part2`() {
        assertEquals(5, DayDD(sample).part2())
    }
}
