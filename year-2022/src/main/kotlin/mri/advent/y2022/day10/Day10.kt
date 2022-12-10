package mri.advent.y2022.day10

import mri.advent.y2022.BaseDay

/** --- Day 10:  --- */
class Day10(inFile: String = "/day10.in") : BaseDay(inFile) {

    // Models --
    enum class InstructionType(val nbCycles: Int) { ADDX(2), NOOP(1) }
    data class Instruction(val type: InstructionType, val arg: Int = 0)

    fun String.toInstruction() = with(this.split(" ")) {
        if (this.size == 1) Instruction(InstructionType.NOOP)
        else Instruction(InstructionType.ADDX, this[1].toInt())
    }

    // Algo ----
    fun solve(part: Int = 1): Any {
        var xRegister = 1
        var currentCycle = 1

        var signalSum = 0 // part 1
        val CRT = Array(6) { CharArray(40) { '.' } }  // part 2

        data.lines().map { it.toInstruction() }.forEach { instruction ->

            // NOOP: 1 cycle |  ADDX : 2 cycles
            repeat(instruction.type.nbCycles) {
                // part1: increment signalStrength
                if (currentCycle == 20 || (currentCycle - 20) % 40 == 0) { // spec: 20th, 60th, 100th, 140th, 180th, and 220th cycles
                    signalSum += xRegister * currentCycle
                }

                // part 2: update CRT screen
                val sprite = IntRange(xRegister - 1, xRegister + 1)
                val (pixelRow, pixelCol) = (currentCycle - 1) / 40 to (currentCycle - 1) % 40
                //println("currentCycle: $currentCycle -  $pixelCol,$pixelCol -  xreg=$xRegister")
                if (pixelCol in sprite) {
                    CRT[pixelRow][pixelCol] = '#'
                }
                currentCycle++
            }
            xRegister += instruction.arg
        }
        if (part == 1) {
            return signalSum
        }
        return "\n" + CRT.joinToString("\n") { String(it) }
    }

    override fun part1() = solve(1)
    override fun part2() = solve(2)
}

fun main() {
    Day10().execute()
}