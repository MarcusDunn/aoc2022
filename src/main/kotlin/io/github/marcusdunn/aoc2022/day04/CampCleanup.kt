package io.github.marcusdunn.aoc2022.day04

import java.nio.file.Path
import kotlin.io.path.useLines

fun part1(path: Path) = path.useLines { lines ->
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
    .map { it.split(",", limit = 2) }
    .map {
        it
            .map { it.split("-").map { it.toInt() } }
            .map { (fst, snd) -> fst..snd }
    }
    .map { (fst, snd) -> fst to snd }

private fun IntRange.overlap(intRange: IntRange) =
        this.first >= intRange.first && this.first <= intRange.last ||
        this.first <= intRange.first && this.last >= intRange.first
private fun IntRange.contains(intRange: IntRange) = intRange.first in this && intRange.last in this