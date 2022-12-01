package io.github.marcusdunn.aoc2022.day01

import io.github.marcusdunn.aoc2022.splitAt
import java.nio.file.Path
import kotlin.io.path.useLines

fun maxElf(path: Path) = path.useLines { lines ->
    lines
        .splitAt { it.isBlank() }
        .map { it.sumOf { it.toInt() } }
        .max()
}

data class TopThree(val first: Int, val second: Int, val third: Int) {
    fun add(value: Int) = when {
        value > first -> TopThree(value, first, second)
        value > second -> TopThree(first, value, second)
        value > third -> TopThree(first, second, value)
        else -> this
    }

    fun sum() = first + second + third
}

fun sumTopThree(path: Path) = path.useLines { lines ->
    lines
        .splitAt { it.isBlank() }
        .map { it.sumOf { it.toInt() } }
        .fold(TopThree(0, 0, 0)) { topThree, value -> topThree.add(value) }
        .sum()
}