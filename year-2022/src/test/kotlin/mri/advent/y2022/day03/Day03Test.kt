package mri.advent.y2022.day03

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * --- Day 03 Test:  ---
 */
class Day03Test {

    val sample = "/day03_sample.in"

    @Test
    fun `test part1`() {
        assertEquals(157, Day03(sample).part1())
    }

    @Test
    fun `test part2`() {
        assertEquals(70, Day03(sample).part2())
    }
}
