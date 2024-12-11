package mri.advent.y2024.day11

import mri.advent.y2024.utils.splitHalf
import mri.advent.y2024.utils.toLongs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11Test {


    @Test
    fun testPartOne() {
        assertEquals(55312L, Day11("125 17").partOne())
    }

    @Test
    fun testPartTwo() {
        assertEquals(65601038650482L, Day11("125 17").partTwo())
    }

    @Test
    fun testSplitStones() {
        fun String.splitStones() = this.splitHalf().map { it.toLong() }

        assertEquals(listOf(1L, 0L), "10".splitStones())
        assertEquals(listOf(10L, 0L), "1000".splitStones())
        assertEquals(listOf(20L, 24L), "2024".splitStones())
    }

    @Test
    fun testTransformRules() {
        // The first stone, 0, becomes a stone marked 1.
        assertEquals(listOf(1L), 0L.transform())

        // The second stone, 1, is multiplied by 2024 to become 2024.
        assertEquals(listOf(2024L), 1L.transform())

        // The third stone, 10, is split into a stone marked 1 followed by a stone marked 0.
        assertEquals(listOf(1L, 0L), 10L.transform())

        // The fourth stone, 99, is split into two stones marked 9.
        assertEquals(listOf(9L, 9L), 99L.transform())

        // The fifth stone, 999, is replaced by a stone marked 2021976.
        assertEquals(listOf(2021976L), 999L.transform())
    }

    @Test
    fun testSamplePart1() {
        assertEquals("253000 1 7".toLongs(), "125 17".toLongs().transform())
        assertEquals("253 0 2024 14168".toLongs(), "253000 1 7".toLongs().transform())
        assertEquals("512072 1 20 24 28676032".toLongs(), "253 0 2024 14168".toLongs().transform())
        assertEquals("512 72 2024 2 0 2 4 2867 6032".toLongs(), "512072 1 20 24 28676032".toLongs().transform())
        // ...
        assertEquals("1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32".toLongs(), "512 72 2024 2 0 2 4 2867 6032".toLongs().transform())
        assertEquals(
            "2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2".toLongs(),
            "1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32".toLongs().transform()
        )
    }

}
