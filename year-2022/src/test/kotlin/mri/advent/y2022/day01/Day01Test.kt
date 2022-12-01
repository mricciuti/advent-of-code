package mri.advent.y2022.day01

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * --- Day 01 Test:  ---
 */
class Day01Test {

    val sample = "/day01_sample.in"

    @Test
    fun `test part1`() {
        assertEquals(24000, Day01(sample).part1())
    }

    @Test
    fun `test part2`() {
        assertEquals(45000, Day01(sample).part2())
    }
}
