package mri.advent.y2022.day11

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * --- Day 11 Test:  ---
 */
class Day11Test {

    val sample = "/day11_sample.in"

    @Test
    fun `test part1`() {
        assertEquals(10605L, Day11(sample).part1())
    }

    @Test
    fun `test part2`() {
        assertEquals(2713310158L, Day11(sample).part2())
    }

    @Test
    fun `test reduce worryLevel`() {
        val monkeys = Day11(sample).parseMonkeys()

        /*
          LONG max : 9 223 372 036 854 775 807

          cas limit (sample) :

           **round 10**:
              monk#0  item = 196 849 870   * 19 = 3 740 147 530 => monkey #2
              monk#2 traite item 3,740,147,530  => au carré ! dépassement LONG MAX

               <=  **round 9** : monky#1 traite 196 849 864 => 196 849 870 vers #0
                  <=  **round 8** : monky#3 traite 196 849 861 => 19 6849 864 vers #0
                      <= **ound 8** : monky#0 traite 10 360 519 => 196 849 861 vers #3
         */

        val samples = listOf(10360519, 196849861, 196849864, 196849870).map { it.toLong() }

        fun testItem(monkey: Day11.Monkey, value: Long): Boolean {
            return (monkey.operation.invoke(value) % monkey.divisibleBy) == 0L
        }

        val expected = samples.map { item -> monkeys.map { monk -> testItem(monk, item ) } }
        println("Expected: $expected")


//        val reducedSamples = samples.map { it }  ==> OK
//        val reducedSamples = samples.map { it / monkeys.map { it.divisibleBy }.sum() }  => NOK
//        val reducedSamples = samples.map { it / monkeys.map { it.divisibleBy }.reduce(Long::times)} => NOK
        val reducedSamples = samples.map { it % monkeys.map { it.divisibleBy }.reduce(Long::times)}  // OK !!


        val reducedResults = reducedSamples.map { item -> monkeys.map { monk -> testItem(monk, item ) } }
        println("Result  : $reducedResults")
        Assertions.assertEquals(expected, reducedResults)


    }
}
