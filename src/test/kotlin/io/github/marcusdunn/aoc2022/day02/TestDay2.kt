package io.github.marcusdunn.aoc2022.day02

import kotlin.io.path.Path
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class TestDay2 {
    @Test
    internal fun `check part 1 sample`() {
        assertEquals("", part1(Path("src/test/resources/day02/sample.txt")))
    }

    @Test
    internal fun `check part 1`() {
        assertEquals("", part1(Path("src/test/resources/day02/input.txt")))
    }

    @Test
    internal fun `check part 2`() {
        assertEquals("", part2(Path("src/test/resources/day02/input.txt")))
    }
}