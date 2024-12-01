package mri.advent.y2024.day01

import mri.advent.y2024.BaseDay
import mri.advent.y2024.utils.toIntPair
import kotlin.math.abs

/** --- Day 01:  https://adventofcode.com/2024/day/01  --- */
class Day01(_data: String? = null) : BaseDay(_data) {

    private fun leftAndRightLists(): Pair<List<Int>, List<Int>> = with(data.lines().map { it.toIntPair() }) {
        return this.map { it.first } to this.map { it.second }
    }

    override fun partOne(): Any {
        val numberColumns = leftAndRightLists()
        val lefts = numberColumns.first.sorted()
        val rights = numberColumns.second.sorted()
        return lefts.mapIndexed { index, i -> abs(rights[index] - i) }.sum()
    }

    override fun partTwo(): Any {
        val numberColumns = leftAndRightLists()
        return numberColumns.first.map { left -> left * numberColumns.second.count { it == left } }.sum()
    }
}

fun main() {
    Day01().run()
}