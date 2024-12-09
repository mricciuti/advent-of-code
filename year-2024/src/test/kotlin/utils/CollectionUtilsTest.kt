package utils

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
}