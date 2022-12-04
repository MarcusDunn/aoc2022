package io.github.marcusdunn.aoc2022.day01

import java.nio.file.Path
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class CalorieCountingTest {
    @Test
    internal fun `check sample`() {
        val maxElf = part1(Path.of("src/test/resources/day01/sample.txt"))
        assertEquals(24000, maxElf)
    }

    @Test
    internal fun `check part 1`() {
        val maxElf = part1(Path.of("src/test/resources/day01/input.txt"))
        assertEquals(70374, maxElf)
    }

    @Test
    internal fun `check sample part 2`() {
        val sumTopThree = part2(Path.of("src/test/resources/day01/sample.txt"))
        assertEquals(45000, sumTopThree)
    }
    @Test
    internal fun `check part 2`() {
        val sumTopThree = part2(Path.of("src/test/resources/day01/input.txt"))
        assertEquals(204610, sumTopThree)
    }
}