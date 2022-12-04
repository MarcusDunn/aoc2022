package io.github.marcusdunn.aoc2022.day03

import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.test.assertEquals

class RucksackReorganizationTest {
    @Test
    fun `check part 1 sample`() {
        val part1 = part1(Path("src/test/resources/day03/sample.txt"))
        assertEquals(157, part1)
    }

    @Test
    fun `check part 1`() {
        val part1 = part1(Path("src/test/resources/day03/input.txt"))
        assertEquals(8153, part1)
    }

    @Test
    fun `check part 2 sample`() {
        val part2 = part2(Path("src/test/resources/day03/sample.txt"))
        assertEquals(70, part2)
    }

    @Test
    fun `check part 2`() {
        val part2 = part2(Path("src/test/resources/day03/input.txt"))
        assertEquals(2342, part2)
    }
}