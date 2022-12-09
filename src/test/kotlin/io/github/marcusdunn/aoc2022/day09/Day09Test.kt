package io.github.marcusdunn.aoc2022.day09

import kotlin.io.path.Path
import kotlin.test.assertEquals
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day09Test {
    @TestFactory
    fun `check simulate link`() = listOf(
        Triple(0 to 0, 0 to 0, 0 to 0),
        Triple(0 to 1, 0 to 0, 0 to 1),
        Triple(0 to 1, 0 to 0, 0 to 2),
        Triple(1 to 0, 0 to 0, 2 to 0),
    )
        .map {
            dynamicTest("head: ${it.second} + tail ${it.third} = ${it.first}") {
                assertEquals(it.first, simulateLink(it.second, it.third))
            }
        }

    @Test
    fun `check part 1 sample`() {
        val part1 = part1(Path("src/test/resources/day09/sample.txt"))
        assertEquals(13, part1)
    }

    @Test
    fun `check part 1`() {
        val part1 = part1(Path("src/test/resources/day09/input.txt"))
        assertEquals(6057, part1)
    }


    @Test
    fun `check part 2 sample`() {
        val part2 = part2(Path("src/test/resources/day09/sample.txt"))
        assertEquals(1, part2)
    }

    @Test
    fun `check part 2 sample 2`() {
        val part2 = part2(Path("src/test/resources/day09/sample2.txt"))
        assertEquals(36, part2)
    }

    @Test
    fun `check part 2`() {
        val part2 = part2(Path("src/test/resources/day09/input.txt"))
        assertEquals(2514, part2)
    }
}