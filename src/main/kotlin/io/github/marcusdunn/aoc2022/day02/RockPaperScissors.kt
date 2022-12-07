package io.github.marcusdunn.aoc2022.day02

import java.nio.file.Path
import kotlin.io.path.useLines

fun part1(path: Path) = solve(path, ::scorePart1)

fun part2(path: Path) = solve(path, ::scorePart2)

fun solve(path: Path, score: (Char, Char) -> Int) = path.useLines { lines ->
    lines
        .map { it.split(" ") }
        .map { it.first().first() to it.last().first() }
        .map { score(it.first, it.second) }
        .sum()
}

fun scorePart1(op: Char, me: Char) = when (op) {
    'A' -> when (me) {
        'X' -> 1 + 3
        'Y' -> 2 + 6
        'Z' -> 3 + 0
        else -> throw IllegalArgumentException("must be one of X, Y, or Z")
    }
    'B' -> when (me) {
        'X' -> 1 + 0
        'Y' -> 2 + 3
        'Z' -> 3 + 6
        else -> throw IllegalArgumentException("must be one of X, Y, or Z")
    }
    'C' -> when (me) {
        'X' -> 1 + 6
        'Y' -> 2 + 0
        'Z' -> 3 + 3
        else -> throw IllegalArgumentException("must be one of X, Y, or Z")
    }
    else -> throw IllegalArgumentException("must be one of A, B, or C")
}

fun scorePart2(op: Char, me: Char) = when (me) {
    'X' -> 0 + when (op) {
        'A' -> 3
        'B' -> 1
        'C' -> 2
        else -> throw IllegalArgumentException("must be one of A, B, or C")
    }

    'Y' -> 3 + when (op) {
        'A' -> 1
        'B' -> 2
        'C' -> 3
        else -> throw IllegalArgumentException("must be one of A, B, or C")
    }

    'Z' -> 6 + when (op) {
        'A' -> 2
        'B' -> 3
        'C' -> 1
        else -> throw IllegalArgumentException("must be one of A, B, or C")
    }

    else -> throw IllegalArgumentException("must be one of X, Y, or Z")
}