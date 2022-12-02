package io.github.marcusdunn.aoc2022.day02

import java.nio.file.Path
import kotlin.io.path.useLines

fun part1(path: Path): String = path.useLines { lines ->
    lines
        .map { it.split(" ") }
        .map { it.first().first() to it.last().first() }
        .map { score(it) }
        .sum()
        .toString()
}

fun score(curr: Pair<Char, Char>): Int {
    val (op, me) = curr
    return when (me) {
        // lose
        'X' -> 0 + when (op) {
            'A' -> 3
            'B' -> 1
            'C' -> 2
            else -> TODO()
        }
        // draw
        'Y' -> 3 + when (op) {
            'A' -> 1
            'B' -> 2
            'C' -> 3
            else -> TODO()
        }
        // win
        'Z' -> 6 + when (op) {
            'A' -> 2
            'B' -> 3
            'C' -> 1
            else -> TODO()
        }
        else -> TODO()
    }
}

fun part2(path: Path): String = path.useLines { lines ->
    TODO()
}