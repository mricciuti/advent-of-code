package mri.advent.y2024.day12

import Day12
import Garden
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Test {

    private val sample = """
RRRRIICCFF
RRRRIICCCF
VVRRRCCFFF
VVRCCCJFFF
VVVVCJJCFE
VVIVCCJJEE
VVIIICJJEE
MIIIIIJJEE
MIIISIJEEE
MMMISSJEEE
    """.trimIndent()

    private val simple1 = """
AAAA
BBCD
BBCC
EEEC
    """.trimIndent()

    private val simple2 = """
OOOOO
OXOXO
OOOOO
OXOXO
OOOOO
    """.trimIndent()


    @Test
    fun testPartOneSimple1() {
        val garden = Garden(simple1.lines())
        assertEquals(140, garden.fencingPrice())
    }

    @Test
    fun testPartOneSimple2() {
        val garden = Garden(simple2.lines())
        assertEquals(772, garden.fencingPrice())
    }

    @Test
    fun testPartOne() {
        assertEquals(1930, Day12(sample).partOne())
    }

    @Test
    fun testPartTwoSimple1() {
        assertEquals(80, Day12(simple1).partTwo())
    }

    @Test
    fun testPartTwoSimple2() {
        assertEquals(436, Day12(simple2).partTwo())
    }

    @Test
    fun testPartTwo() {
        assertEquals(1206, Day12(sample).partTwo())
    }

    @Test
    fun testPart2Sides() {
        val garden = """
aaa
a.a
        """.trimIndent().let { Garden(it.lines()) }
        assertEquals(8, garden.regions.first { it.type == 'a' }.sides())

    }

    @Test
    fun debugPart2() {
        val sample = """
AAAA
ABBA
        """.trimIndent()
        val garden = Garden(sample.lines())
        garden.regions.forEach {
            it.plots.forEach { plot ->
                println("  plot $plot")
            }

        }
    }

}