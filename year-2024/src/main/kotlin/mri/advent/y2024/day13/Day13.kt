package mri.advent.y2024.day13

import mri.advent.y2024.BaseDay
import mri.advent.y2024.utils.*
import mri.advent.y2024.utils.geo2d.Vec2D

/* --- Day 13: Claw Contraption https://adventofcode.com/2024/day/13  --- */

data class Machine(val moveA: Vec2D, val moveB: Vec2D, val prizeLocation: Vec2D) {

    // part 1 - brute force
    fun minCost(): Long? {
        repeat(100) { x ->
            repeat(100) { y ->
                if (prizeLocation.equals(moveA.mult(x).plus(moveB.mult(y)))) {
                    return 3L * x + y
                }
            }
        }
        return null
    }

    // part 2 - solve system
    // https://fr.wikipedia.org/wiki/R%C3%A8gle_de_Cramer
    fun solve(): Long? {
        val offset = 10000000000000
        /* System
         *   x  * a1  +  y * b1 = c1    // X axis
         *   x  * a2  +  y * b2 = c2    // Y axis
         *
         *   D = a1 * b2 - a2 * b1
         */
        val a = moveA.x to moveA.y
        val b = moveB.x to moveB.y
        val c = prizeLocation.x + offset to prizeLocation.y + offset
        val D = a.first * b.second - a.second * b.first
        if (D == 0) {
            println("no unique solution ")
            return null
        }
        val x = (c.first * b.second - c.second * b.first).toLong().divWithRem(D.toLong())
        val y = (a.first * c.second - a.second * c.first).toLong().divWithRem(D.toLong())
        if (x.second != 0L || y.second != 0L) {
            println("invalid solution ")
            return null
        }
        return x.first * 3L + y.first
    }


}

// Extract position from given line :
//   "Button B: X+22, Y+67"     ==>   22,67
//   "Prize: X=8400, Y=5400"    ==>   8400,5400
val regex = "\\d+".toRegex()
fun String.parsePosition(): Vec2D {
    val ints = regex.findAll(this).map { it.value }.toList()
    return Vec2D(ints.first().toInt(), ints.last().toInt())
}

fun List<String>.toMachine() = Machine(first().parsePosition(), get(1).parsePosition(), last().parsePosition())


class Day13(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    val machines = data.asChunks().map { it.toMachine() }

    fun dump() {
        machines.forEach {
            println("${it.moveA.x};${it.moveA.y};${it.moveB.x};${it.moveB.y};${it.prizeLocation.x};${it.prizeLocation.y}")
        }
    }

    override fun partOne() = machines.sumOf { it.minCost() ?: 0 }
    override fun partTwo() = machines.sumOf { it.solve() ?: 0 }
}

fun main() {
    Day13().run()
}