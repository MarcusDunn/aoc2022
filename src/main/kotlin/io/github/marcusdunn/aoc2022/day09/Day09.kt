package io.github.marcusdunn.aoc2022.day09

import java.nio.file.Path
import kotlin.io.path.useLines
import kotlin.math.sign

typealias Vec2 = Pair<Int, Int>

fun part1(path: Path) = buildSet {
    parse(path)
        .fold((0 to 0) to (0 to 0)) { (tail, head), dir ->
            add(tail)
            val newHead = newHead(dir, head)
            val newTail = simulateLink(newHead, tail)
            add(newTail)
            newTail to newHead
        }
}.also { println(it) }.size

fun part2(path: Path): Int {
    var running = List(10) { 0 to 0 }
    val tailPoses = mutableSetOf<Vec2>()

    for (direction in parse(path)) {
        require(running.size == 10) { "${running.size} was not 10 in $running" }
        val next = mutableListOf(newHead(direction, running.first()))
        for (i in running.indices) {
            val nextHead = next[i]
            val tail = running.getOrNull(i + 1) ?: continue
            val newTail = simulateLink(nextHead, tail)
            next.add(newTail)
        }
        println("$running + $direction $next")
        require(running.size == 10) { "${running.size} was not 10 in $running" }
        require(next.size == 10) { "${next.size} was not 10 in $next" }
        tailPoses.add(running.last())
        tailPoses.add(next.last())
        running = next
    }
    return tailPoses.size
}

fun simulateLink(
    newHead: Pair<Int, Int>,
    tail: Pair<Int, Int>
): Pair<Int, Int> =
    if (touching(tail, newHead)) tail else tail.copy(
        first = tail.first - (tail.first - newHead.first).sign,
        second = tail.second - (tail.second - newHead.second).sign,
    )

private fun newHead(
    dir: Direction,
    head: Pair<Int, Int>
): Pair<Int, Int> {
    val newHead = when (dir) {
        Direction.Right -> head.copy(first = head.first + 1)
        Direction.Left -> head.copy(first = head.first - 1)
        Direction.Up -> head.copy(second = head.second + 1)
        Direction.Down -> head.copy(second = head.second - 1)
    }
    return newHead
}

private fun Collection<Vec2>.printVisited() {
    val (minX, maxX) = minOf { it.first } to maxOf(maxOf { it.second }, 5)
    val (minY, maxY) = minOf { it.first } to maxOf(maxOf { it.second }, 4)
    (minY..maxY).reversed().forEach { y ->
        (minX..maxX).forEach { x ->
            print(if (this.contains(x to y)) '#' else '.')
        }
        println()
    }
    println()
    println()
}

private fun Collection<Vec2>.printAll(vararg vec: Vec2) {
    val (minX, maxX) = minOf { it.first } to maxOf(maxOf { it.second }, 5)
    val (minY, maxY) = minOf { it.first } to maxOf(maxOf { it.second }, 4)
    (minY..maxY).reversed().forEach { y ->
        (minX..maxX).forEach { x ->
            if (vec.contains(x to y)) print('#') else print('.')
        }
        println()
    }
    println()
    println()
}

private fun Collection<Vec2>.printCombo(head: Vec2, tail: Vec2) {
    val (minX, maxX) = minOf { it.first } to maxOf(maxOf { it.second }, 5)
    val (minY, maxY) = minOf { it.first } to maxOf(maxOf { it.second }, 4)
    (minY..maxY).reversed().forEach { y ->
        (minX..maxX).forEach { x ->
            when (x to y) {
                head -> print('H')
                tail -> print('T')
                else -> print('.')
            }
        }
        println()
    }
    println()
    println()
}

fun touching(a: Vec2, b: Vec2) = (a.first - b.first in -1..1 && a.second - b.second in -1..1)

private fun Vec2.goTo(head: Vec2): Pair<Int, Int> {
    val dx = first - head.first
    val dy = second - head.second
    println("diff $dx $dy")
    return copy(first = first - dx / 2, second = second - dy / 2)
}


enum class Direction {
    Right, Left, Up, Down
}

fun parse(path: Path): List<Direction> = path.useLines {
    it.flatMapTo(mutableListOf()) {
        val (dir, mag) = it.split(" ")
        val direction = when (dir.first()) {
            'R' -> Direction.Right
            'L' -> Direction.Left
            'U' -> Direction.Up
            'D' -> Direction.Down
            else -> throw IllegalArgumentException("Invalid direction: $dir")
        }
        List(mag.toInt()) { direction }
    }
}