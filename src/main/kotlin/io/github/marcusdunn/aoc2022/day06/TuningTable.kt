package io.github.marcusdunn.aoc2022.day06

import java.nio.file.Path
import kotlin.io.path.readText

fun part1(path: Path) = path
    .readText()
    .windowed(4)
    .withIndex()
    .filter { (_, str) -> str.toSet().size == 4 }
    .map { it.index + 4 }
    .first()

fun part2(path: Path) = path
    .readText()
    .windowed(14)
    .withIndex()
    .filter { (_, str) -> str.toSet().size == 14 }
    .map { it.index + 14 }
    .first()