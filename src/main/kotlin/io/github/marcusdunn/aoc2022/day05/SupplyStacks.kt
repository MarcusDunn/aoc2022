package io.github.marcusdunn.aoc2022.day05

import java.nio.file.Path
import kotlin.io.path.readText

fun part1(path: Path) = solve(CrateMover9000, path)

fun part2(path: Path) = solve(CrateMover9001, path)

private fun solve(crane: Crane, path: Path): String {
    val (crates, commands) = path.readText().split("\n\n")
    val rowsWithIndex = crates
        .lines()
        .map { line ->
            line
                .chunked(4)
                .map { it[1] }
                .map { if (it == ' ') null else it }
        }
    val rows = rowsWithIndex.dropLast(1)
    val indexes = rowsWithIndex
        .last()
        .map { it ?: throw NullPointerException("last row contained null values") }
        .map { it.digitToInt() }
    val columns = indexes
        .associateWith { idx ->
            rows
                .map { it.getOrNull(idx - 1) }
                .reversed()
                .filterNotNull()
        }
    return commands
        .lines()
        .map { line ->
            line
                .split("move", "from", "to")
                .filter { it.isNotBlank() }
                .map { it.trim() }
                .map { it.toInt() }
        }
        .map { (move, from, to) -> crane.createCommand(move, from, to) }
        .fold(columns) { acc, command -> command.execute(acc) }
        .values
        .map { it.last() }
        .joinToString(separator = "")
}

private fun interface Crane {
    fun createCommand(amount: Int, from: Int, to: Int): Command
}

private fun interface Command {
    fun execute(columns: Map<Int, List<Char>>): Map<Int, List<Char>>
}

private val CrateMover9000 = Crane { amount, from, to ->
    Command { columns ->
        columns.toMutableMap().apply {
            val fromCol = getOrElse(from) { throw IndexOutOfBoundsException("no entry for $from") }
            val toCol = getOrElse(to) { throw IndexOutOfBoundsException("no entry for $to") }
            set(to, toCol + fromCol.takeLast(amount).reversed())
            set(from, fromCol.dropLast(amount))
        }
    }
}

private val CrateMover9001 = Crane { amount, from, to ->
    Command { columns ->
        columns.toMutableMap().apply {
            val fromCol = getOrElse(from) { throw IndexOutOfBoundsException("no entry for $from in $this") }
            val toCol = getOrElse(to) { throw IndexOutOfBoundsException("no entry for $from in $this") }
            set(to, toCol + fromCol.takeLast(amount))
            set(from, fromCol.dropLast(amount))
        }
    }
}