package io.github.marcusdunn.aoc2022.day10

import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.test.assertEquals

class CathodeRayTubeTest {
    @Test
    fun `check part 1 sample0`() {
        val part1 = part1(Path("src/test/resources/day10/sample0.txt"))
        assertEquals(0, part1)
    }

    @Test
    fun `check part 1 sample`() {
        val part1 = part1(Path("src/test/resources/day10/sample.txt"))
        assertEquals(13140, part1)
    }

    @Test
    fun `check part 1`() {
        val part1 = part1(Path("src/test/resources/day10/input.txt"))
        assertEquals(14720, part1)
    }

    @Test
    fun `check part 2`() {
        val part1 = part2(Path("src/test/resources/day10/input.txt"))
        assertEquals(
            """
            ####.####.###..###..###..####.####.####.
            #.......#.#..#.#..#.#..#.#.......#.#....
            ###....#..###..#..#.###..###....#..###..
            #.....#...#..#.###..#..#.#.....#...#....
            #....#....#..#.#....#..#.#....#....#....
            #....####.###..#....###..#....####.#....
            .""".trimIndent(),
            part1
        )
    }
}