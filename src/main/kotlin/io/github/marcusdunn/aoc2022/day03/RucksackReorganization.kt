package io.github.marcusdunn.aoc2022.day03

import java.nio.file.Path
import kotlin.io.path.useLines

fun part1(path: Path) = path.useLines { lines ->
    lines
        .map { it.take(it.length / 2) to it.takeLast(it.length / 2) }
        .flatMap { (fst, snd) -> fst.filter { it in snd }.toList().distinct() }
        .sumOf { value(it) }
}

fun part2(path: Path) = path.useLines { lines ->
    lines
        .chunked(3)
        .flatMap { (fst, snd, thd) -> fst.filter { it in snd && it in thd }.toList().distinct() }
        .sumOf { value(it) }
}

fun value(it: Char) = when (it) {
    in 'a'..'z' -> it - 'a' + 1
    in 'A'..'Z' -> it - 'A' + 27
    else -> throw IllegalArgumentException("$it must be in a..z or A..Z")
}
