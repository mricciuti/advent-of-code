package mri.advent.y2022.day02

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import mri.advent.y2022.day02.Day02.Shape.*
import mri.advent.y2022.day02.Day02.RoundOutcome.*

/**
 * --- Day 02 Test:  ---
 */
class Day02Test {

    val sample = "/day02_sample.in"

    @Test
    fun `test part1`() {
        assertEquals(15, Day02(sample).part1())
    }

    @Test
    fun `test part2`() {
        assertEquals(12, Day02(sample).part2())
    }

    @Test
    fun `test shape versus`() {
        val rules = listOf(
            (ROCK to ROCK) to DRAW, (ROCK to PAPER) to LOST, (ROCK to SCISSORS) to WON,
            (PAPER to ROCK) to WON, (PAPER to PAPER) to DRAW, (PAPER to SCISSORS) to LOST,
            (SCISSORS to ROCK) to LOST, (SCISSORS to PAPER) to WON, (SCISSORS to SCISSORS) to DRAW,
        )
        rules.forEach {
            assertEquals(it.second, it.first.first.versus(it.first.second))
        }

    }
}
