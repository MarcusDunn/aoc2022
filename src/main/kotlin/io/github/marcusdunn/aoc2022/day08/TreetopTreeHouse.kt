package io.github.marcusdunn.aoc2022.day08

import io.github.marcusdunn.aoc2022.transpose
import java.nio.file.Path
import kotlin.io.path.useLines

fun part1(path: Path): Int = parse(path).let { grid ->
    buildSet {
        addHorizontalToSet(grid)
        addHorizontalToSet(grid.transposeSquare()) { (i, j) -> j to i }
    }.count()
}

fun part2(path: Path): Int = parse(path).let { grid ->
    buildMap {
        addHorizontalToMap(grid)
        addHorizontalToMap(grid.transposeSquare()) { (fst, snd) -> snd to fst }
    }.maxOf { it.value }
}

private fun List<List<Int>>.transposeSquare() = transpose().map { it.map { it!! } }
fun parse(path: Path) = path.useLines { lines ->
    lines.map { it.map { it.digitToInt() } }.toList()
}

private fun MutableMap<Pair<Int, Int>, Int>.addHorizontalToMap(
    grid: List<List<Int>>,
    calcKey: (Pair<Int, Int>) -> Pair<Int, Int> = { it }
) = grid
    .forEachIndexed { i, row ->
        row.forEachIndexed { j, tree ->
            compute(calcKey(i to j)) { _, left ->
                (left ?: 1) * scoreHorizontal(
                    tree = tree,
                    right = row.take(maxOf(0, j)),
                    left = row.takeLast(row.size - j - 1)
                )
            }
        }
    }


private fun MutableSet<Pair<Int, Int>>.addHorizontalToSet(
    grid: List<List<Int>>,
    calcKey: (Pair<Int, Int>) -> Pair<Int, Int> = { it }
) = grid
    .forEachIndexed { i, row ->
        row.forEachIndexed { j, tree ->
            if (seenHorizontal(
                    tree = tree,
                    right = row.map { it }.take(maxOf(0, j)),
                    left = row.map { it }.takeLast(row.size - j - 1)
                )
            ) {
                add(calcKey(i to j))
            }
        }
    }

private fun scoreHorizontal(tree: Int, right: List<Int>, left: List<Int>): Int {
    val rightScore = right.takeLastWhile { it < tree }.count().let { if (it == right.size) it else it + 1 }
    val leftScore = left.takeWhile { it < tree }.count().let { if (it == left.size) it else it + 1 }
    return rightScore * leftScore
}

private fun seenHorizontal(tree: Int, right: List<Int>, left: List<Int>) =
    right.all { it < tree } || left.all { it < tree }