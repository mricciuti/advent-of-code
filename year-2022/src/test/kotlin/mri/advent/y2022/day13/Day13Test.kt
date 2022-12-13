package mri.advent.y2022.day13

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * --- Day 13 Test:  ---
 */
class Day13Test {

    val sample = "/day13_sample.in"

    @Test
    fun `test part1`() {
        assertEquals(13, Day13(sample).part1())
    }

    @Test
    fun `test part2`() {
        assertEquals(140, Day13(sample).part2())
    }

    @Test
    fun testParsePacket() {
        val packet = "[[1],[2,3,4]]".toPacket()
        assertNotNull(packet)
    }

}
