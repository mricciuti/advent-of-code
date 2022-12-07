package mri.advent.y2022.day07

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * --- Day 07 Test:  ---
 */
class Day07Test {

    val sample = "/day07_sample.in"

    @Test
    fun `test part1`() {
        assertEquals(95437L, Day07(sample).part1())
    }

    @Test
    fun `test part2`() {
        assertEquals(24933642L, Day07(sample).part2())
    }
}
