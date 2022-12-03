package mri.advent.y2022.day03

import mri.advent.y2022.BaseDay

/** --- Day 03:  --- */
class Day03(inFile: String = "/day03.in") : BaseDay(inFile) {

    fun itemPriority(item: Char) = if (item.isLowerCase()) 1 + (item - 'a') else 27 + (item - 'A')

    override fun part2(): Any {
        with(data.lines()) {
            return (0 until this.size / 3)
                    //  3-lines groups
                .map { this.subList(it * 3, it * 3 + 3) }
                    // item commun aux 3 lignes
                .map { list -> list.first().first { list[1].contains(it) && list[2].contains(it) } }
                    // somme par prio
                .sumOf { itemPriority(it) }
        }
    }

    override fun part1(): Any {
        return data.lines()
            .map { it.take(it.length / 2) to it.substring(it.length / 2) }
            .map { compartments -> compartments.first.first { compartments.second.contains(it) } }
            .sumOf { itemPriority(it) }
    }
}

fun main() {
    Day03().execute()
}