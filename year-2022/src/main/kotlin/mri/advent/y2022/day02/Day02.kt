package mri.advent.y2022.day02

import mri.advent.y2022.BaseDay

/** --- Day 02:  --- */
class Day02(inFile: String = "/day02.in") : BaseDay(inFile) {

    enum class RoundOutcome(val score: Int) { LOST(0), DRAW(3), WON(6) }

    enum class Shape(val score: Int) {
        ROCK(1), PAPER(2), SCISSORS(3);

        fun versus(other: Shape) =
            if (this == other) RoundOutcome.DRAW
            else if (this == ROCK && other == SCISSORS || this == PAPER && other == ROCK || this == SCISSORS && other == PAPER) RoundOutcome.WON
            else RoundOutcome.LOST
    }

    private fun parseOutcome(asChar: Char) = RoundOutcome.values()[asChar - 'X']
    private fun parseShape(shapeChar: Char) = Shape.values()[if (shapeChar >= 'X') shapeChar - 'X' else shapeChar - 'A']

    data class Round(val myHand: Shape, val otherHand: Shape) {
        private val outcome = myHand.versus(otherHand)
        fun score() = myHand.score + outcome.score
    }

    // part 2 -------------------------
    private fun decideShape(otherHand: Shape, expectedOutput: RoundOutcome) = Shape.values().first { it.versus(otherHand) == expectedOutput }

    override fun part2(): Any {

        // input:  list (  opponentShape -> expected outcome )
        val guide = data.lines()
            .map { it.split(" ") }
            .map { parseShape(it[0].first()) to parseOutcome(it[1].first()) }

        return guide.map {
            Round(otherHand = it.first, myHand = decideShape(it.first, it.second))
        }.sumOf { it.score() }
    }

    // part 1 -------------------------
    override fun part1(): Any {

        // input:  list ( opponentShape -> myShape )
        val guide = data.lines()
            .map { it.split(" ") }
            .map { parseShape(it[0].first()) to parseShape(it[1].first()) }

        return guide
            .map { Round(otherHand = it.first, myHand = it.second) }
            .sumOf { it.score() }
    }
}

fun main() {
    Day02().execute()
}