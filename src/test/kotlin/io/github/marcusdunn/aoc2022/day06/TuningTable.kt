package io.github.marcusdunn.aoc2022.day06

import kotlin.io.path.Path
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class TuningTable {
    @Test
    internal fun `check part 1`() {
        val part1 = part1(Path("src/test/resources/day06/input.txt"))
        assertEquals(0, part1)
    }

    @Test
    internal fun `check part 1 sample`() {
        val part1 = part1(Path("src/test/resources/day06/sample.txt"))
        assertEquals(0, part1)
    }
}