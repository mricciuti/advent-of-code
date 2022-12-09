package mri.advent.y2022.day09

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * --- Day 09 Test:  ---
 */
class Day09Test {

    val sample = "/day09_sample.in"

    @Test
    fun `test part1`() {
        assertEquals(13, Day09(sample).part1())
    }

    @Test
    fun `test part2`() {
        assertEquals(36, Day09("/day09_sample2.in").part2())
    }
}
