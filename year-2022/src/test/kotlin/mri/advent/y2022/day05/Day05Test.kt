package mri.advent.y2022.day05

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * --- Day 05 Test:  ---
 */
class Day05Test {

    val sample = "/day05_sample.in"

    @Test
    fun `test part1`() {
        assertEquals(7, Day05(sample).part1())
    }

    @Test
    fun `test part2`() {
        assertEquals(5, Day05(sample).part2())
    }
}
