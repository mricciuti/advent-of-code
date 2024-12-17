package mri.advent.y2024.day17

import mri.advent.y2024.day17.Day17.Program
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day17Test {

    private val sample = """
Register A: 729
Register B: 0
Register C: 0

Program: 0,1,5,4,3,0
    """.trimIndent()

    @Test
    fun testPartOne() {
        assertEquals("4,6,3,5,6,3,5,2,1,0", Day17(sample).partOne())
    }

    @Test
    fun testCase1() {
        with(execProgram(0, 0, 9, listOf(2, 6))) {
            assertEquals(1L, this.B)
        }
    }

    @Test
    fun testCase2() {
        with(execProgram(10, 0, 0, listOf(5, 0, 5, 1, 5, 4))) {
            assertEquals(listOf(0, 1, 2).map { it.toLong()  }, this.outputs)
        }
    }

    @Test
    fun testCase3() {
        with(execProgram(2024, 0, 0, listOf(0, 1, 5, 4, 3, 0))) {
            assertEquals(listOf(4, 2, 5, 6, 7, 7, 7, 7, 3, 1, 0).map { it.toLong()  }, this.outputs)
            assertEquals(0L, this.A)
        }
    }

    @Test
    fun testCase4() {
        with(execProgram(0, 29, 0, listOf(1, 7))) {
            assertEquals(26L, this.B)
        }
    }

    @Test
    fun testCase5() {
        with(execProgram(2024, 0, 43690, listOf(4, 0))) {
            assertEquals(44354L, this.B)
        }
    }

    private fun execProgram(A: Long, B: Long, C: Long, numbers: List<Int>) = Program(A, B, C, numbers).apply { run() }

    @Test
    fun testPartTwo() {
        assertEquals(1000, Day17(sample).partTwo())
    }

}
