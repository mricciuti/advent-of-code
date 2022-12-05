package mri.advent.y2022.day05

import mri.advent.y2022.BaseDay

/** --- Day 05:  --- */
class Day05(inFile: String = "/day05.in") : BaseDay(inFile) {

    override fun part1() = solve(false)
    override fun part2() = solve(true)

    fun parseStacks(stacksLine: String, nbStacks: Int): MutableList<Char> {
        val padded = stacksLine.padEnd(nbStacks * 5, ' ')
        return (0 until nbStacks)
            .map { padded[1 + it * 4] }
            .toMutableList()
    }

    /*   format:  move X from Y to Z    */
    data class Move(val amount: Int, val from: Int, val to: Int)
    fun parseMove(line: String) = with(line.split(" ")) { Move(this[1].toInt(), this[3].toInt(), this[5].toInt()) }

    fun solve(part2: Boolean): Any {

        val lines = data.lines()
        val sectionsSeparatorIdx = lines.indexOfFirst { it.contains("1") }
        val nbStacks = lines[sectionsSeparatorIdx].last().digitToInt()
        val stacks = Array(nbStacks) { mutableListOf<Char>() }

        // parse stacks section
        lines.take(sectionsSeparatorIdx)
            .map { parseStacks(it, nbStacks) }
            .reversed()
            .forEach {
                it.forEachIndexed { index, c -> if (c != ' ') stacks[index].add(c) }
            }

        // process moves
        lines.subList(sectionsSeparatorIdx + 2, lines.size)
            .map { parseMove(it) }
            .forEach { move ->
                if (part2) {
                    stacks[move.to - 1].addAll(stacks[move.from - 1].takeLast(move.amount))
                    (0 until move.amount).forEach { stacks[move.from - 1].removeLast() }
                } else {
                    repeat(move.amount) {
                        stacks[move.to - 1].add(stacks[move.from - 1].removeLast())
                    }
                }
            }
        return String(stacks.map { it.last() }.toCharArray())
    }
}

fun main() {
    Day05().execute()
}