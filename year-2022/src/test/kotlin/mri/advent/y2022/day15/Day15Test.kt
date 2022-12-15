package mri.advent.y2022.day15

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * --- Day 15 Test:  ---
 */
class Day15Test {

    val sample = "/day15_sample.in"

    @Test
    fun `test part1`() {
        assertEquals(26, Day15(sample).solve1(10))
    }

    @Test
    fun `test part2`() {
        assertEquals(56000011L, Day15(sample).solve2(20))
    }

}
