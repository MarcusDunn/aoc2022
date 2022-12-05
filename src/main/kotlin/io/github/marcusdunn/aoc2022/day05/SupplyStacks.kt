package io.github.marcusdunn.aoc2022.day05

import java.nio.file.Path
import kotlin.io.path.readText

fun part1(path: Path): String {
    val (crates, commands) = path.readText().split("\n\n")
    val rowsWithIndex = crates
        .lines()
        .map { line ->
            line
                .chunked(4)
                .map { if (it.getOrNull(1) == ' ') null else it.getOrNull(1) }
        }
    val rows = rowsWithIndex.dropLast(1)
    val index = rowsWithIndex.last()
    val columns = index
        .map { it!!.digitToInt() }
        .associateWith { idx ->
            rows
                .map { it.getOrNull(idx - 1) }
                .reversed()
                .filterNotNull()
        }
    return commands
        .lines()
        .map {
            it.split("move", "from", "to").filter { it.isNotBlank() }.map { it.trim().toInt() }
        }
        .map { (move, from, to) -> Command(move, from, to) }
        .fold(columns) { acc, command -> command.apply(acc) }
        .values
        .map { it.last() }
        .joinToString(separator = "")
}

fun part2(path: Path): String {
    val (crates, commands) = path.readText().split("\n\n")
    val rowsWithIndex = crates
        .lines()
        .map { line ->
            line
                .chunked(4)
                .map { if (it.getOrNull(1) == ' ') null else it.getOrNull(1) }
        }
    val rows = rowsWithIndex.dropLast(1)
    val index = rowsWithIndex.last()
    val columns = index
        .map { it!!.digitToInt() }
        .associateWith { idx ->
            rows
                .map { it.getOrNull(idx - 1) }
                .reversed()
                .filterNotNull()
        }
    return commands
        .lines()
        .map {
            it.split("move", "from", "to").filter { it.isNotBlank() }.map { it.trim().toInt() }
        }
        .map { (move, from, to) -> Command2(move, from, to) }
        .fold(columns) { acc, command -> command.apply(acc) }
        .values
        .map { it.last() }
        .joinToString(separator = "")
}

data class Command(private val move: Int, private val from: Int, private val to: Int) {
    fun apply(columns: Map<Int, List<Char>>): Map<Int, List<Char>> = columns.toMutableMap().apply {
        val fromCol = get(from)!!
        val toCol = get(to)!!
        set(to, toCol + fromCol.takeLast(move).reversed())
        set(from, fromCol.dropLast(move))
    }
}

data class Command2(private val move: Int, private val from: Int, private val to: Int) {
    fun apply(columns: Map<Int, List<Char>>): Map<Int, List<Char>> = columns.toMutableMap().apply {
        val fromCol = get(from)!!
        val toCol = get(to)!!
        set(to, toCol + fromCol.takeLast(move))
        set(from, fromCol.dropLast(move))
    }
}