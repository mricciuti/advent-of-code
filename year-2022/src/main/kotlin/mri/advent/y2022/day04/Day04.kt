package mri.advent.y2022.day04

import mri.advent.y2022.BaseDay

/** --- Day 04:  --- */
class Day04(inFile: String = "/day04.in") : BaseDay(inFile) {

    // parsing
    fun parseRange(range: String): IntRange {
        val parts = range.split(("-"))
        return IntRange(parts[0].toInt(), parts[1].toInt())
    }

    fun parseLine(line: String): Pair<IntRange, IntRange> {
        val sections = line.split(",")
        return parseRange(sections[0]) to parseRange(sections[1])
    }

    override fun part2(): Any {
        return data.lines()
            .map { parseLine(it) }
            .count { it.first.intersect(it.second).isNotEmpty() }
    }

    override fun part1(): Any {
        return data.lines()
            .map { parseLine(it) }
            .map { it.first.toList() to it.second.toList() }
            .count { it.first.containsAll(it.second) || it.second.containsAll(it.first) }
    }
}

fun main() {
    Day04().execute()
}