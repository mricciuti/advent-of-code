package mri.advent.y2024.day03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03Test {

    private val sample = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
    private val sample2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

    @Test
    fun testPartOne() {
        assertEquals(161L, Day03(sample).partOne())
    }

    @Test
    fun testPartTwo() {
        assertEquals(48L, Day03(sample2).partTwo())
    }

    @Test
    fun testExtractInstructions() {
        val day03 = Day03("")
        assertEquals(listOf(MulInstruction(2, 4)), day03.extractInstructions("mul(2,4)").toList())
        assertEquals(0, day03.extractInstructions("mul(4*, mul(6,9!, ?(12,34)").toList().size)
        assertEquals(4, day03.extractInstructions(sample).toList().size)
    }

    @Test
    fun testExtractEnabledSections() {
        val day03 = Day03("")
        assertEquals(listOf("aaa"), day03.extractEnabledSections("aaadon't()bbb"))
        assertEquals(listOf("aaa", "ccc"), day03.extractEnabledSections("aaadon't()bbbdo()cccdon't()ddd"))
        assertEquals(listOf("xmul(2,4)&mul[3,7]!^", "?mul(8,5))"), day03.extractEnabledSections(sample2))
    }
}
