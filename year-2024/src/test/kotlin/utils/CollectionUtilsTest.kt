package utils

import mri.advent.y2024.utils.consecutiveRanges
import mri.advent.y2024.utils.pairs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CollectionUtilsTest {

    @Test
    fun testCreatePairs() {
        val list = listOf(1, 2, 3)
        val pairs = list.pairs()
        assertEquals(pairs.size, 3)
        assertTrue(pairs.contains(Pair(1, 2)))
        assertTrue(pairs.contains(Pair(1, 3)))
        assertTrue(pairs.contains(Pair(2, 3)))
    }

    @Test
    fun testCreateConsecutiveRanges() {
        assertEquals(emptyList<IntRange>(), emptyList<Int>().consecutiveRanges())
        assertEquals(listOf(1..1), listOf(1).consecutiveRanges())
        assertEquals(listOf(1..2), listOf(1, 2).consecutiveRanges())
        assertEquals(listOf(1..2, 4..7, 9..9), listOf(1, 2, 4, 5, 6, 7, 9).consecutiveRanges())
    }
}