package io.github.marcusdunn.aoc2022.day04

import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.test.assertEquals

class CampCleanupTest {

    @Test
    fun `check part 1 sample`() {
        val valid = part1(Path("src/test/resources/day04/sample.txt"))
        assertEquals(2, valid)
    }

    @Test
    fun `check part 1`() {
        val part1 = part1(Path("src/test/resources/day04/input.txt"))
        assertEquals(602, part1)
    }

    @Test
    fun `check part 2`() {
        val part2 = part2(Path("src/test/resources/day04/input.txt"))
        assertEquals(891, part2)
    }
}