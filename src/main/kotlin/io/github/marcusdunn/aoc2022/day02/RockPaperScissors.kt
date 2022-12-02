package io.github.marcusdunn.aoc2022.day02

import java.nio.file.Path
import kotlin.io.path.useLines

fun part2(path: Path) = path.useLines { lines ->
    lines
        .map { it.split(" ") }
        .map { it.first().first() to it.last().first() }
        .map { score(it.first, it.second) }
        .sum()
}

fun score(op: Char, me: Char) = when (me) {
    // lose
    'X' -> 0 + when (op) {
        'A' -> 3
        'B' -> 1
        'C' -> 2
        else -> throw IllegalArgumentException("must be one of A, B, or C")
    }
    // draw
    'Y' -> 3 + when (op) {
        'A' -> 1
        'B' -> 2
        'C' -> 3
        else -> throw IllegalArgumentException("must be one of A, B, or C")
    }
    // win
    'Z' -> 6 + when (op) {
        'A' -> 2
        'B' -> 3
        'C' -> 1
        else -> throw IllegalArgumentException("must be one of A, B, or C")
    }

    else -> throw IllegalArgumentException("must be one of X, Y, or Z")
}