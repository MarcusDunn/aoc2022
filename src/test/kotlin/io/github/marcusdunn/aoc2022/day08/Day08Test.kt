package io.github.marcusdunn.aoc2022.day08

import kotlin.io.path.Path
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Day08Test {
    @Test
    fun `check part 1 sample`() {
        val part1 = part1(Path("src/test/resources/day08/sample.txt"))
        assertEquals(21, part1)
    }

    @Test
    fun `check part 1`() {
        val part1 = part1(Path("src/test/resources/day08/input.txt"))
        assertEquals(1776, part1)
    }

    @Test
    fun `check part 2 sample`() {
        val part2 = part2(Path("src/test/resources/day08/sample.txt"))
        assertEquals(8, part2)
    }

    @Test
    fun `check part 2`() {
        val part2 = part2(Path("src/test/resources/day08/input.txt"))
        assertEquals(234416, part2)
    }
}