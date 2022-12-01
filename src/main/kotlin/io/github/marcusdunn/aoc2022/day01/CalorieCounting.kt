package io.github.marcusdunn.aoc2022.day01

import java.lang.Integer.max
import java.nio.file.Path
import kotlin.io.path.useLines

fun maxElf(path: Path) = path.useLines { lines ->
    lines.fold(0 to 0) { (max, curr), line ->
        if (line.isBlank()) {
            max(max, curr) to 0
        } else {
            max to curr + line.toInt()
        }
    }
}.let { max(it.first, it.second) }

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
    lines.fold(TopThree(0, 0, 0) to 0) { (topThree, curr), line ->
        if (line.isBlank()) {
            topThree.add(curr) to 0
        } else {
            topThree to curr + line.toInt()
        }
    }
}.let { it.first.add(it.second) }.sum()