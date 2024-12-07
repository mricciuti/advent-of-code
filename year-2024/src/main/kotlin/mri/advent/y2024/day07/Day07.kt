package mri.advent.y2024.day07

import mri.advent.y2024.BaseDay
import mri.advent.y2024.utils.toLongs

/** --- Day 07: Bridge Repair  https://adventofcode.com/2024/day/07  --- */
class Day07(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    data class Equation(val result: Long, val numbers: List<Long>) {

        fun solvable(availableOperators: List<Operator> = listOf(Operator.ADD, Operator.MULTIPLY)): Boolean {
            fun recursiveCheck(currentResult: Long, currentIndex: Int): Boolean {
                // end-of-recursion conditions:
                if (currentIndex > numbers.lastIndex) return false
                if (currentResult > this.result) return false
                if (currentIndex == numbers.lastIndex) return currentResult == this.result

                // recursion: iteration
                return availableOperators.any { operator ->
                    val nextIndex = currentIndex + 1
                    recursiveCheck(operator.fct.invoke(currentResult, numbers[nextIndex]), nextIndex)
                }
            }
            return recursiveCheck(numbers.first(), 0)
        }
    }

    // parsing input:  190: 10 19
    private fun String.toEquation() = this.split(": ").let { Equation(it.first().toLong(), it.last().toLongs()) }


    enum class Operator(val fct: (a: Long, b: Long) -> Long) {
        ADD(fct = { a, b -> a + b }),
        MULTIPLY(fct = { a, b -> a * b }),
        CONCAT(fct = { a, b -> "$a$b".toLong() }),
    }

    private fun solve(operators: List<Operator>) = data.lines().map { it.toEquation() }
        .filter { it.solvable(operators) }
        .sumOf { it.result }

    override fun partOne() = solve(listOf(Operator.ADD, Operator.MULTIPLY))
    override fun partTwo() = solve(listOf(Operator.ADD, Operator.MULTIPLY, Operator.CONCAT))

}

fun main() {
    Day07().run()
    /*
        Part 1: 1611660863222
            => time : 59 ms
        Part 2: 945341732469724
            => time : 403 ms
     */
}