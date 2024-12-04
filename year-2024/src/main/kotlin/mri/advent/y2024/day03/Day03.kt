package mri.advent.y2024.day03

import mri.advent.y2024.BaseDay

/** --- Day 03: Mull It Over   (https://adventofcode.com/2024/day/03)  --- */

// instructions like mul(X,Y), where X and Y are each 1-3 digit numbers.
val INSTRUCTION_PATTERN = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
data class MulInstruction(val x: Long, val y: Long) {
    val result = x * y
}

class Day03(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    // extract valid instructions from given memory section
    fun extractInstructions(section: String) = INSTRUCTION_PATTERN.findAll(section)
        //.onEach { result -> println("found matching result: ${result.groupValues}") }
        .map { MulInstruction(it.groupValues[1].toLong(), it.groupValues[2].toLong()) }

    // sum of all instruction results in a given memory section
    fun sectionValue(section: String) = extractInstructions(section).sumOf { it.result }

    // part 2 : extract enabled sections between "do()" and "don't()" separators
    fun extractEnabledSections(memory: String) = memory.split("do()").map { it.split("don't()")[0] }

    @Override
    override fun partOne() = sectionValue(data)

    @Override
    override fun partTwo() = extractEnabledSections(data).sumOf { sectionValue(it) }
}

fun main() {
    Day03().run()
}