package io.github.marcusdunn.aoc2022.day02

import kotlin.io.path.Path
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class TestDay2 {
    @Test
    internal fun `check part 2`() {
        assertEquals(14184, part2(Path("src/test/resources/day02/input.txt")))
    }
}