package io.github.marcusdunn.aoc2022.day09

import kotlin.io.path.Path
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Day09Test {
    @Test
    fun `check part 1 sample`() {
        val part1 = part1(Path("src/test/resources/day09/sample.txt"))
        assertEquals(-1, part1)
    }

    @Test
    fun `check part 1`() {
        val part1 = part1(Path("src/test/resources/day09/input.txt"))
        assertEquals(-1, part1)
    }

    @Test
    fun `check part 2 sample`() {
        val part2 = part2(Path("src/test/resources/day09/sample.txt"))
        assertEquals(-1, part2)
    }

    @Test
    fun `check part 2`() {
        val part2 = part2(Path("src/test/resources/day09/input.txt"))
        assertEquals(-1, part2)
    }
}