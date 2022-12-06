package io.github.marcusdunn.aoc2022.day04

import java.nio.file.Path
import kotlin.io.path.useLines

fun part1(path: Path) = solve(path) { fst, snd -> fst contains snd || snd contains fst }

fun part2(path: Path) = solve(path) { fst, snd -> fst overlap snd }

private fun solve(path: Path, pred: (IntRange, IntRange) -> Boolean) = path.useLines { lines ->
    lines
        .parse()
        .count { pred(it.first, it.second) }
}

private fun Sequence<String>.parse() = this
    .map { it.split(",", limit = 2) }
    .map {
        it
            .map { it.split("-").map { it.toInt() } }
            .map { (fst, snd) -> fst..snd }
    }
    .map { (fst, snd) -> fst to snd }

private infix fun IntRange.overlap(intRange: IntRange) = first in intRange || intRange.first in this
private infix fun IntRange.contains(intRange: IntRange) = intRange.first in this && intRange.last in this