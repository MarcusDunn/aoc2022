package io.github.marcusdunn.aoc2022.day02

import java.nio.file.Path
import kotlin.io.path.useLines

fun part1(path: Path) = solve(path, ::scorePart1V2)

fun part2(path: Path) = solve(path, ::scorePart2)

fun solve(path: Path, score: (Char, Char) -> Int) = path.useLines { lines ->
    lines
        .map { it.split(" ") }
        .map { (op, me) -> op.first() to me.first() }
        .map { score(it.first, it.second) }
        .sum()
}

fun scorePart1V2(op: Char, me: Char) = when (me) {
    'X' -> 1 + when (op) {
        'A' -> 3
        'B' -> 0
        'C' -> 6
        else -> throw IllegalArgumentException("$me must be A, B, or C")
    }

    'Y' -> 2 + when (op) {
        'A' -> 6
        'B' -> 3
        'C' -> 0
        else -> throw IllegalArgumentException("$me must be A, B, or C")
    }

    'Z' -> 3 + when (op) {
        'A' -> 0
        'B' -> 6
        'C' -> 3
        else -> throw IllegalArgumentException("$me must be A, B, or C")
    }

    else -> throw IllegalArgumentException("$me must be R, P, or S")
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