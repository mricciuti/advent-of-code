package mri.advent.y2022.day11

import mri.advent.y2022.BaseDay

/** --- Day 11:  --- */
class Day11(inFile: String = "/day11.in") : BaseDay(inFile) {

    data class Monkey(val items: MutableList<Long> = mutableListOf(),
                      val operation: (Long) -> Long, //Function<Long>,
                      val divisibleBy: Long,
                      val targetTrue: Int, val targetFalse: Int) {
        var inspectionsCount = 0L
    }

    private fun String.toOperation(): (Long) -> Long {
        val (op, argStr) = this.substringAfter("new = old ").split(" ")
        return { x: Long ->
            val arg = if (argStr == "old") x else argStr.toLong()
            if (op == "+") x + arg else x * arg
        }
    }

    private fun String.lastInt() = this.substringAfterLast(" ").toInt()

    private fun String.toMonkey(): Monkey {
        val lines = this.split("\r\n")
        return Monkey(
            lines[1].substringAfter(": ").split(",").map { it.trim().toLong() }.toMutableList(),
            lines[2].toOperation(), lines[3].lastInt().toLong(), lines[4].lastInt(), lines[5].lastInt()
        )
    }

    fun parseMonkeys() = data.split("\r\n\r\n").map { it.toMonkey() }.toList()

    private fun solve(part: Int = 1): Any {
        val monkeys = parseMonkeys()

        repeat(if (part == 1) 20 else 10000) { round ->
            monkeys.forEachIndexed { index, monkey ->
                while (monkey.items.isNotEmpty()) {
                    val item = monkey.items.removeFirst()
                    monkey.inspectionsCount += 1

                    var worryLevel = monkey.operation.invoke(item)

                    if (part == 1) {
                        worryLevel /= 3
                    }
                    val targetMonkey = if (worryLevel % monkey.divisibleBy == 0L) monkey.targetTrue else monkey.targetFalse
                    if (part == 2) {
                        // "You'll need to find another way to keep your worry levels manageable...."

                        // => but: reduire la valeur du worryLevel
                        // !! ne pas impacter les tests de division des prochaines étapes
                        val commonDenominator = monkeys.map { it.divisibleBy }.reduce(Long::times)
                        worryLevel %= commonDenominator
                    }
                    if (worryLevel < 0) {
                        throw RuntimeException("limite atteinte: round=$round monkey#$index  item=$item => worry=$worryLevel")
                    }
                    monkeys[targetMonkey].items.add(worryLevel)
                }
            }
        }
        // take the two most active monkey ,  multiplie their number of inspected items
        return monkeys.map { it.inspectionsCount }.sortedDescending().take(2).reduce(Long::times)
    }

    override fun part1() = solve(part = 1)
    override fun part2() = solve(part = 2)
}

fun main() {
    Day11().execute()
}