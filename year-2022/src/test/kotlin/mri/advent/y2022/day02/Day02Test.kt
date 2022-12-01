package mri.advent.y2022.day02

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * --- Day 02 Test:  ---
 */
class Day02Test {

    val sample = "/day02_sample.in"

    @Test
    fun `test part1`() {
        assertEquals(7, Day02(sample).part1())
    }

    @Test
    fun `test part2`() {
        assertEquals(5, Day02(sample).part2())
    }
}
