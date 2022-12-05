package io.github.marcusdunn.aoc2022.day05

import kotlin.io.path.Path
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class SupplyStacksTest {
    @Test
    internal fun `check part 1 sample`() {
        val part1 = part1(Path("src/test/resources/day05/sample.txt"))
        assertEquals("CMZ", part1)
    }

    @Test
    internal fun `check part 1`() {
        val part1 = part1(Path("src/test/resources/day05/input.txt"))
        assertEquals("LBLVVTVLP", part1)
    }

    @Test
    internal fun `check part 2 sample`() {
        val part1 = part2(Path("src/test/resources/day05/sample.txt"))
        assertEquals("MCD", part1)
    }

    @Test
    internal fun `check part 2`() {
        val part1 = part2(Path("src/test/resources/day05/input.txt"))
        assertEquals("TPFFBDRJD", part1)
    }
}