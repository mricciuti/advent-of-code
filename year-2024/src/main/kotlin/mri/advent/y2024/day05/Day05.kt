package mri.advent.y2024.day05

import mri.advent.y2024.BaseDay
import mri.advent.y2024.utils.asChunks
import mri.advent.y2024.utils.toInts

/* --- Day 05: Print Queue   https://adventofcode.com/2024/day/05  --- */

data class Rule(val leftPage: Int, val rightPage: Int)
fun Set<Rule>.hasRule(left: Int, right: Int) = this.any { it.leftPage == left && it.rightPage == right }

data class Update(val pages: List<Int>) {
    fun middlePage() = pages[pages.size / 2]

    fun valid(rules: Set<Rule>): Boolean {
        (pages.lastIndex downTo 1).forEach { rightPageIndex ->
            var (rightPage, leftPages) = pages[rightPageIndex] to pages.take(rightPageIndex)
            if (leftPages.any { rules.hasRule(rightPage, it) }) {
                return false
            }
        }
        return true
    }

    fun sorted(rules: Set<Rule>) = Update(this.pages.sortedWith { leftPage, rightPage ->
        if (rules.hasRule(leftPage, rightPage)) -1
        else if (rules.hasRule(rightPage, leftPage)) 1
        else 0
    })
}

class Day05(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    // input data is:  a section with rules then  and a section with updates
    val rulesAndUpdates = data.asChunks()
    val rules = rulesAndUpdates.first().map { it.toInts("|").let { Rule(it.first(), it.last()) } }.toSet()
    val updates = rulesAndUpdates.last().map { Update(it.toInts(",")) }

    // --- Part 1
    override fun partOne() = updates
        .filter { it.valid(rules) }
        .sumOf { it.middlePage() }

    // --- Part 2
    override fun partTwo() = updates
        .filter { !it.valid(rules) }  // only incorrectly-ordered updates
        .map { it.sorted(rules) } // fix order
        .sumOf { it.middlePage() }
}

fun main() {
    Day05().run()
}
