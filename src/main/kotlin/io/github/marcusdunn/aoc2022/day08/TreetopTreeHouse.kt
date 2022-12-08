package io.github.marcusdunn.aoc2022.day08

import io.github.marcusdunn.aoc2022.transpose
import java.nio.file.Path
import kotlin.io.path.useLines

fun scoreHorizontal(tree: Int, right: List<Int>, left: List<Int>) =
    ((right
        .ifEmpty { return 0 }
        .takeLastWhile { it < tree }
        .count().let { if (it == right.size) it else it + 1 }) *
            (left
                .ifEmpty { return 0 }
                .takeWhile { it < tree }
                .count().let { if (it == left.size) it else it + 1 }))

fun part2(path: Path): Int = path.useLines { lines ->
    val grid = lines
        .map { it.map { it.digitToInt() } }
        .toList()
    val b = grid
        .flatMapIndexed { i, row ->
            row.mapIndexed { j, tree ->
                (i to j) to scoreHorizontal(
                    tree = tree,
                    right = row.take(maxOf(0, j)),
                    left = row.takeLast(row.size - j - 1)
                )
            }
        }.toMap()
    val a = grid
        .transpose()
        .flatMapIndexed { i, row ->
            row.mapIndexed { j, tree ->
                (j to i) to scoreHorizontal(
                    tree = tree!!,
                    right = row.map { it!! }.take(maxOf(0, j)),
                    left = row.map { it!! }.takeLast(row.size - j - 1)
                )
            }
        }
        .toMap()
    a.mapValues { (k, v) -> v * b[k]!! }.values.max()
}

fun seenHorizontal(tree: Int, right: List<Int>, left: List<Int>) =
    (right.all { it < tree } || left.all { it < tree })

fun part1(path: Path): Int = path.useLines { lines ->
    val grid = lines
        .map { it.map { it.digitToInt() }.withIndex().toList() }
        .toList()
        .withIndex()
        .toList()
    grid
        .flatMap { (i, row) ->
            row.filter { (j, tree) ->
                seenHorizontal(
                    tree = tree,
                    right = row.map { it.value }.take(maxOf(0, j)),
                    left = row.map { it.value }.takeLast(row.size - j - 1)
                )
            }.map { i to it.index }
        }
        .toSet()
        .union(grid
            .map { it.value.map { it.value } }
            .transpose()
            .map { it.withIndex().toList() }
            .withIndex()
            .toList()
            .flatMap { (i, row) ->
                row.filter { (j, tree) ->
                    seenHorizontal(
                        tree = tree!!,
                        right = row.map { it.value!! }.take(maxOf(0, j)),
                        left = row.map { it.value!! }.takeLast(row.size - j - 1)
                    )
                }.map { it.index to i }
            }.toSet()
        )
        .sortedBy { it.first * 10 + it.second }
        .count()
}