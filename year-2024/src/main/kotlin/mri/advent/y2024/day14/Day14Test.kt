package mri.advent.y2024.day14

import mri.advent.y2024.utils.geo2d.Vec2D
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Day14Test {

    private val sample = """
p=0,4 v=3,-3
p=6,3 v=-1,-3
p=10,3 v=-1,2
p=2,0 v=2,-1
p=0,0 v=1,3
p=3,0 v=-2,-2
p=7,6 v=-1,-3
p=3,0 v=-1,-2
p=9,3 v=2,3
p=7,3 v=-1,2
p=2,4 v=2,-3
p=9,5 v=-3,-3
    """.trimIndent()


    @Test
    fun testParse() {
        val robot = "p=9,5 v=-3,-3".toRobot()
        assertEquals(Vec2D(9, 5), robot.position)
        assertEquals(Vec2D(-3, -3), robot.velocity)
    }

    @Test
    fun testPartOne() {
        assertEquals(12, Day14(sample).partOne())
    }

    @Test
    fun testPartTwo() {
        assertTrue(true, "nothing to test")
    }
}
