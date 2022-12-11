package io.github.marcusdunn.aoc2022.day11

import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.test.assertEquals

class MonkeyInTheMiddleTest {
    @Test
    fun `check part 1 sample`() {
        val part1 = part1(Path("src/test/resources/day11/sample.txt"))
        assertEquals(10605, part1)
    }

    @Test
    fun `check part 1`() {
        val part1 = part1(Path("src/test/resources/day11/input.txt"))
        assertEquals(113232, part1)
    }
}