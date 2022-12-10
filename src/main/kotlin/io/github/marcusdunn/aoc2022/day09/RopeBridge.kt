package io.github.marcusdunn.aoc2022.day09

import java.nio.file.Path
import kotlin.io.path.useLines
import kotlin.math.sign

typealias Vec2 = Pair<Int, Int>

fun part1(path: Path) = solve(path, 2)
fun part2(path: Path) = solve(path, 10)

fun solve(path: Path, size: Int) = buildSet {
    parse(path).fold(List(size) { 0 to 0 }) { running, direction ->
        running
            .drop(1)
            .foldIndexed(listOf(newHead(direction, running.first()))) { i, next, tail ->
                next + simulateLink(next[i], tail)
            }
            .also { add(it.last()) }
    }
}.size

fun simulateLink(
    newHead: Pair<Int, Int>,
    tail: Pair<Int, Int>
): Pair<Int, Int> =
    if (touching(tail, newHead)) tail else tail.copy(
        first = tail.first + (newHead.first - tail.first).sign,
        second = tail.second + (newHead.second - tail.second).sign,
    )

private fun newHead(
    dir: Direction,
    head: Pair<Int, Int>
): Pair<Int, Int> = when (dir) {
    Direction.Right -> head.copy(first = head.first + 1)
    Direction.Left -> head.copy(first = head.first - 1)
    Direction.Up -> head.copy(second = head.second + 1)
    Direction.Down -> head.copy(second = head.second - 1)
}

fun touching(a: Vec2, b: Vec2) = a.first - b.first in -1..1 && a.second - b.second in -1..1

enum class Direction {
    Right, Left, Up, Down;

    companion object {
        fun fromChar(char: Char) = when (char) {
            'R' -> Right
            'L' -> Left
            'U' -> Up
            'D' -> Down
            else -> throw IllegalArgumentException("Invalid direction: $char")
        }
    }
}

fun parse(path: Path): List<Direction> = path.useLines {
    it.flatMapTo(mutableListOf()) {
        val (dir, mag) = it.split(" ")
        List(mag.toInt()) { Direction.fromChar(dir.first()) }
    }
}