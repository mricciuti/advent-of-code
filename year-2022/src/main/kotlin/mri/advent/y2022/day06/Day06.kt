package mri.advent.y2022.day06

import mri.advent.y2022.BaseDay

/** --- Day 06:  --- */
class Day06(inFile: String = "/day06.in") : BaseDay(inFile) {

    fun solve(buffSize: Int): Any {
        for (idx in 0 until data.length - buffSize)
            if (data.substring(idx, idx + buffSize).toList().distinct().size == buffSize) {
                return idx + buffSize
        }
        return -1
    }

    override fun part1() = solve(4)
    override fun part2() = solve(14)
}

fun main() {
    Day06().execute()
}