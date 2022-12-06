package mri.advent.y2022.day06

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * --- Day 06 Test:  ---
 */
class Day06Test {

    val sample = "/day06_sample.in"

    @Test
    fun `test part1`() {
        assertEquals(7, Day06(sample).part1())
    }
    @Test
    fun `test part2`() {
        assertEquals(19, Day06(sample).part2())
    }

}
