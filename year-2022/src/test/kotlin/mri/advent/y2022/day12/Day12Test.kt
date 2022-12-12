package mri.advent.y2022.day12

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * --- Day 12 Test:  ---
 */
class Day12Test {

    val sample = "/day12_sample.in"

    @Test
    fun `test part1`() {
        assertEquals(31, Day12(sample).part1())
    }

    @Test
    fun `test part2`() {
        assertEquals(29, Day12(sample).part2())
    }
}
