package io.github.marcusdunn.aoc2022.day06

import java.nio.file.Path
import kotlin.io.path.readText

fun part1(path: Path) = solve(path, 4)

fun part2(path: Path) = solve(path, 14)

private fun solve(path: Path, uniqueCount: Int) = path
    .readText()
    .windowedSequence(uniqueCount) { it.toSet() }
    .indexOfFirst { it.size == uniqueCount } + uniqueCount