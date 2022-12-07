package io.github.marcusdunn.aoc2022.day02

import kotlin.io.path.Path
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class RockPaperScissorsTest {
    @Test
    fun `check part 1 sample`() {
        val part1 = part1(Path("src/test/resources/day02/sample.txt"))
        assertEquals(15, part1)
    }

    @Test
    internal fun `check part 1`() {
        assertEquals(13675, part1(Path("src/test/resources/day02/input.txt")))
    }
    @Test
    internal fun `check part 2`() {
        assertEquals(14184, part2(Path("src/test/resources/day02/input.txt")))
    }
}