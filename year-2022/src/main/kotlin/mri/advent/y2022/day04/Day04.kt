package mri.advent.y2022.day04

import mri.advent.y2022.BaseDay

/** --- Day 04:  --- */
class Day04(inFile: String = "/day04.in") : BaseDay(inFile) {

    // IntRange helper extensions
    fun IntRange.includes(other: IntRange) = this.first <= other.first && this.last >= other.last
    fun IntRange.overlapsWith(other: IntRange) = other.first in this || other.last in this

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
            .map { parseLine(it)}
            .filter {  it.first.overlapsWith(it.second) || it.second.overlapsWith(it.first)  }
            .size
    }

    override fun part1(): Any {
        return data.lines()
            .map { parseLine(it)}
            .filter {  it.first.includes(it.second) || it.second.includes(it.first)  }
            .size
    }
}

fun main() {
    Day04().execute()
}