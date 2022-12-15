package mri.advent.y2022.day14

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * --- Day 14 Test:  ---
 */
class Day14Test {

    val sample = "/day14_sample.in"

    @Test
    fun `test part1`() {
        assertEquals(24, Day14(sample).part1())
    }

    @Test
    fun `test part2`() {
        assertEquals(93, Day14(sample).part2())
    }
}
