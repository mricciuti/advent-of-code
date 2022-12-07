package mri.advent.y2022.day06

import mri.advent.y2022.BaseDay

/** --- Day 06:  --- */
class Day06(inFile: String = "/day06.in") : BaseDay(inFile) {

    fun solve(buffSize: Int): Any {
        return buffSize + data.windowed(buffSize).withIndex()
            .first { it.value.toCharArray().distinct().size == buffSize }
            .index
    }

    override fun part1() = solve(4)
    override fun part2() = solve(14)
}

fun main() {
    Day06().execute()
}