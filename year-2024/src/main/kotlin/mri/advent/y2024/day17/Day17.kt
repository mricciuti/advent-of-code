package mri.advent.y2024.day17

import mri.advent.y2024.BaseDay
import kotlin.math.pow

/** --- Day 17: Chronospatial Computer  https://adventofcode.com/2024/day/17  --- */

enum class OpCode { ADV, BXL, BST, JNZ, BXC, OUT, BDV, CDV }

fun Int.toOpCode() = OpCode.entries.firstOrNull { it.ordinal.equals(this) } ?: throw IllegalArgumentException("Unknown opcode $this")

class Day17(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    data class Program(var A: Long, var B: Long, var C: Long, val numbers: List<Int>) {
        var instructionPointer = 0
        var jumpTo: Int? = null
        val outputs = mutableListOf<Long>()

        val operations = mutableMapOf<OpCode, (Int) -> Unit>()
        fun registerOperation(code: OpCode, fct: (Int) -> Unit) = fct.also { operations.put(code, it) }

        private fun aDiv(operand: Int) = (A.toDouble().div(2.0.pow(combo(operand).toDouble()))).toLong()

        init {
            // register available operations
            registerOperation(OpCode.ADV) { operand -> A = aDiv(operand) }
            registerOperation(OpCode.BXL) { operand -> B = B.xor(operand.toLong()) }
            registerOperation(OpCode.BST) { operand -> B = combo(operand).rem(8) }
            registerOperation(OpCode.JNZ) { operand -> if (A != 0L) jumpTo = operand.toInt() }
            registerOperation(OpCode.BXC) { _       -> B = B.xor(C) }
            registerOperation(OpCode.OUT) { operand -> outputs.add(combo(operand).rem(8)) }
            registerOperation(OpCode.BDV) { operand -> B = aDiv(operand) }
            registerOperation(OpCode.CDV) { operand -> C = aDiv(operand) }
        }

        private fun combo(operand: Int) = when (operand) {
            in 0..3 -> operand.toLong()
            4 -> A
            5 -> B
            6 -> C
            else -> throw IllegalArgumentException("Invalid operand $operand")
        }

        fun exec(opCode: Int, operand: Int) {
            println("execute operation $opCode with operand $operand")
            operations[opCode.toOpCode()]?.invoke(operand)
                ?: throw IllegalArgumentException("Invalid operation $opCode")
        }

        fun run() {
            var executed = 0
            while (instructionPointer < numbers.size) {
                exec(numbers[instructionPointer], numbers[instructionPointer + 1])
                instructionPointer = jumpTo ?: (instructionPointer + 2)
                jumpTo = null
                println(this)
                if (executed++ > 10000) throw IllegalStateException("stack overflow...")
            }
        }

        override fun toString() = "   prog state: A: $A, B: $B, C: $C, pointer: $instructionPointer,  outputs: ${outputs.joinToString(",")}"
    }

    fun List<String>.toProgram(): Program {
        val (a, b, c) = this.take(3).map { it.substringAfter(": ").toLong() }
        val instrs = this.last().substringAfter(": ").split(",").map { it.toInt() }
        return Program(a, b, c, instrs)
    }

    val program = data.lines().toProgram()

    override fun partOne() = program.apply { run() }.outputs.joinToString(",")

    override fun partTwo() = "TODO"

}

fun main() {
    Day17().run()
}