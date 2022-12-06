package io.github.marcusdunn.aoc2022.day06

import kotlin.io.path.Path
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class TuningTable {
    @Test
    internal fun `check part 1`() {
        val part1 = part1(Path("src/test/resources/day06/input.txt"))
        assertEquals(1582, part1)
    }

    @Test
    internal fun `check part 1 sample`() {
        val part1 = part1(Path("src/test/resources/day06/sample.txt"))
        assertEquals(7, part1)
    }
    @Test
    internal fun `check part 2`() {
        val part2 = part2(Path("src/test/resources/day06/input.txt"))
        assertEquals(3588, part2)
    }

    @Test
    internal fun `check part 2 sample`() {
        val part2 = part2(Path("src/test/resources/day06/sample.txt"))
        assertEquals(19, part2)
    }
}