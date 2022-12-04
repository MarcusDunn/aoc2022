package io.github.marcusdunn.aoc2022.day04

import java.nio.file.Path
import kotlin.io.path.useLines

fun part1(path: Path): Int = path.useLines { lines ->
    lines
        .parse()
        .count { (fst, snd) -> fst.contains(snd) || snd.contains(fst) }
}

fun part2(path: Path) = path.useLines { lines ->
    lines
        .parse()
        .count { (fst, snd) -> fst.overlap(snd) }
}

private fun Sequence<String>.parse() = this
    .map { it.split(",") }
    .map {
        it
            .map { it.split("-").map { it.toInt() } }
            .map { (fst, snd) -> fst..snd }
    }

fun IntRange.overlap(intRange: IntRange) = intRange.any { it in this }
fun IntRange.contains(intRange: IntRange) = intRange.first in this && intRange.last in this