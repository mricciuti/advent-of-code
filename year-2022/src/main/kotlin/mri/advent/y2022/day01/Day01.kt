package mri.advent.y2022.day01

import mri.advent.y2022.BaseDay

/** --- Day 01:  ---  */
class Day01(inFile: String = "/day01.in") : BaseDay(inFile) {

    fun elfCalories(data: String): List<Int> {
        return data.split("\r\n\r\n")
            .map { it.split("\r\n").map { it.toInt() }.sum() }
    }

    override fun part2(): Any {
        return elfCalories(data).sortedDescending().take(3).sum()
    }

    override fun part1(): Any {
        return elfCalories(data).max()
    }
}

fun main() {
    Day01().execute()
}