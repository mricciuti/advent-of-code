package mri.advent.y2024.day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Day10Test {

    private val sample = """
89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732
    """.trimIndent()

    @Test
    fun testPartOne() {
        assertEquals(36, Day10(sample).partOne())
    }

    @Test
    fun simpleCase1() {
        val lines = """
0123
1234
8765
9876
        """.trimIndent().lines()
        val map = Day10.TopographicMap(lines)
        assertEquals(1, map.hikingTrails.map { it.start() to it.end() }.toSet().size)
    }


    @Test
    fun simpleCase2() {
        val data = """
...0...
...1...
...2...
6543456
7.....7
8.....8
9.....9
        """.trimIndent().replace(".", ".")
        val map = Day10.TopographicMap(data.lines())
        assertEquals(2, map.hikingTrails.size)
    }

    @Test
    fun simpleCase3() {
        val data = """
..90..9
...1.98
...2..7
6543456
765.987
876....
987....
        """.trimIndent()
        val map = Day10.TopographicMap(data.lines())
        assertEquals(4, map.hikingTrails.distinctBy { trail -> trail.start() to trail.end() }.size)
    }

    @Test
    fun simpleCase4() {
        val data = """
10..9..
2...8..
3...7..
4567654
...8..3
...9..2
.....01
        """.trimIndent()
        val map = Day10.TopographicMap(data.lines())

        // This topographic map contains two trailheads;
        // the trailhead at the top has a score of 1, while the trailhead at the bottom has a score of 2:
        assertEquals(2, map.hikingTrails.distinctBy { trail -> trail.start() }.size)
        assertEquals(3, map.hikingTrails.distinctBy { trail -> trail.start() to trail.end() }.size)

    }


    @Test
    fun part2Case1() {
        val lines = """
.....0.
..4321.
..5..2.
..6543.
..7..4.
..8765.
..9....
        """.trimIndent().lines()
        val map = Day10.TopographicMap(lines)
        assertEquals(3, map.hikingTrails.size)
    }

    @Test
    fun testPartTwo() {
        assertEquals(81, Day10(sample).partTwo())
    }

}
