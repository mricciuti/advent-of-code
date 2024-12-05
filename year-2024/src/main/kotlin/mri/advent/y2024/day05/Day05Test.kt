package mri.advent.y2024.day05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Day05Test {

    private val sample = """
47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47
    """.trimIndent()

    @Test
    fun testPartOne() {
        assertEquals(143, Day05(sample).partOne())
    }

    @Test
    fun testPartTwo() {
        assertEquals(123, Day05(sample).partTwo())
    }

    fun rule(pair: Pair<Int, Int>) = Rule(pair.first, pair.second)
    fun update(vararg pages: Int) = Update(pages.toList())

    @Test
    fun testSetHasRule() {
        val rules = setOf(rule(1 to  2), rule(3 to 4))
        assertTrue(rules.hasRule(1, 2))
    }

    @Test
    fun testUpdateValid() {
        assertTrue(update(1, 2, 3, 4, 5).valid(emptySet()))

        val rules = setOf(rule(10 to 20), rule(20 to 30), rule(30 to 40))
        assertTrue(update(10, 20, 30, 40).valid(rules))
        assertTrue(update(10, 30, 40).valid(rules))
        assertFalse(update(40, 20, 30).valid(rules))
    }

}
