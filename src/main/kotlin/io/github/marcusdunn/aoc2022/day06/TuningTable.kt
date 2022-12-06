package io.github.marcusdunn.aoc2022.day06

import java.nio.file.Path
import kotlin.io.path.readText

fun part1(path: Path) = path
    .readText()
    .asSequence()
    .windowed(14)
    .withIndex()
    .filter { (idx, str) -> str.toSet().size == 14 }
    .map { it.index + 14 }
    .onEach { println(it) }
    .first()