package io.github.marcusdunn.aoc2022.day07

import kotlin.io.path.Path
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class NoSpaceLeftOnDevice {
    @Test
    internal fun `check sample part 1`() {
        val part1 = part1(Path("src/test/resources/day07/sample.txt"))
        assertEquals(95437, part1)
    }

    @Test
    internal fun `check part 1`() {
        val part1 = part1(Path("src/test/resources/day07/input.txt"))
        assertEquals(1315285, part1)
    }

    @Test
    internal fun `check sample part 2`() {
        val part2 = part2(Path("src/test/resources/day07/sample.txt"))
        assertEquals(24933642, part2)
    }

    @Test
    internal fun `check part 2`() {
        val part2 = part2(Path("src/test/resources/day07/input.txt"))
        assertEquals(9847279, part2)
    }
}