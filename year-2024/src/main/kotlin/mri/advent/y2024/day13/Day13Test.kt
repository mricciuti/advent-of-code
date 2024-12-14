package mri.advent.y2024.day13

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day13Test {

    private val sample = """
Button A: X+94, Y+34
Button B: X+22, Y+67
Prize: X=8400, Y=5400

Button A: X+26, Y+66
Button B: X+67, Y+21
Prize: X=12748, Y=12176

Button A: X+17, Y+86
Button B: X+84, Y+37
Prize: X=7870, Y=6450

Button A: X+69, Y+23
Button B: X+27, Y+71
Prize: X=18641, Y=10279
    """.trimIndent()

    @Test
    fun testPartOne() {
        assertEquals(480L, Day13(sample).partOne())
    }

    @Test
    fun sample() {
        Day13(sample).dump()
    }

    @Test
    fun testOneEquationPart2() {
        val machineNoSolution = """
Button A: X+94, Y+34
Button B: X+22, Y+67
Prize: X=8400, Y=5400
        """.trimIndent().lines().toMachine()
        assertEquals(null, machineNoSolution.solve())

        val machineWithSolution = """
Button A: X+26, Y+66
Button B: X+67, Y+21
Prize: X=12748, Y=12176
        """.trimIndent().lines().toMachine()
        assertEquals(459236326669L, machineWithSolution.solve())
    }


    @Test
    fun testPartTwo() {
        assertEquals(875318608908L, Day13(sample).partTwo())
    }

}
