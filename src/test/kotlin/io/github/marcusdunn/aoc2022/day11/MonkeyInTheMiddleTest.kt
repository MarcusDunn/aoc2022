package io.github.marcusdunn.aoc2022.day11

import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.test.assertEquals

class MonkeyInTheMiddleTest {
    @Test
    fun `check part 2 sample`() {
        val part2 = part2(Path("src/test/resources/day11/sample.txt"))
        assertEquals(2713310158, part2)
    }

    @Test
    fun `check part 2`() {
        val part2 = part2(Path("src/test/resources/day11/input.txt"))
        assertEquals(29703395016, part2)
    }
}