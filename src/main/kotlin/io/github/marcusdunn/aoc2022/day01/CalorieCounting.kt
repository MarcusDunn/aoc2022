package io.github.marcusdunn.aoc2022.day01

import io.github.marcusdunn.aoc2022.CapacityRestrictedPriorityQueue
import io.github.marcusdunn.aoc2022.CapacityRestrictedPriorityQueue.Companion.invoke
import io.github.marcusdunn.aoc2022.splitAt
import java.nio.file.Path
import kotlin.io.path.useLines

fun part1(path: Path) = getTop(path, 1)
fun part2(path: Path) = getTop(path, 3)

private fun getTop(path: Path, count: Int) = path.useLines { lines ->
    lines
        .splitAt { it.isBlank() }
        .map { it.sumOf { line -> line.toInt() } }
        .fold(CapacityRestrictedPriorityQueue<Int>(count)) { queue, value -> queue.apply { add(value) } }
        .sum()
}