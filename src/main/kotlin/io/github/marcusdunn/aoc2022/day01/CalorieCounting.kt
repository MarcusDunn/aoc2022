package io.github.marcusdunn.aoc2022.day01

import io.github.marcusdunn.aoc2022.CapacityRestrictedPriorityQueue
import io.github.marcusdunn.aoc2022.CapacityRestrictedPriorityQueue.Companion.invoke
import io.github.marcusdunn.aoc2022.splitAt
import java.nio.file.Path
import kotlin.io.path.useLines

fun maxElf(path: Path) = getTop(path, 1)

fun sumTopThree(path: Path) = getTop(path, 3)

private fun getTop(path: Path, count: Int) = path.useLines {
    it
        .splitAt { it.isBlank() }
        .map { it.sumOf { it.toInt() } }
        .fold(CapacityRestrictedPriorityQueue<Int>(count)) { queue, value -> queue.apply { add(value) } }
        .sum()
}