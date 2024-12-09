package mri.advent.y2024.day09

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day09Test {

    private val sample = "2333133121414131402".trimIndent()

    @Test
    fun testPartOne() {
        assertEquals(1928L, Day09(sample).partOne())
    }

    @Test
    fun testPartTwo() {
        assertEquals(2858L, Day09(sample).partTwo())
    }

}
