package io.github.marcusdunn.aoc2022.day05

import java.nio.file.Path
import kotlin.io.path.readText

fun part1(path: Path) = solve(CrateMover9000, path)

fun part2(path: Path) = solve(CrateMover9001, path)

private fun solve(crane: Crane, path: Path) = parse(path)
    .let { (crates, commands) ->
        commands
            .fold(crates) { acc, command -> crane.runCommand(command, acc) }
            .values
            .flatten()
            .joinToString("")
    }

typealias Crates = Map<Int, List<Char>>

private fun parse(path: Path): Pair<Crates, List<Command>> {
    val (crates, commandsString) = path.readText().split("\n\n")
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
    val commands = commandsString
        .lines()
        .map { line ->
            line
                .split("move", "from", "to")
                .filter { it.isNotBlank() }
                .map { it.trim() }
                .map { it.toInt() }
        }
        .map { (amount, from, to) -> Command(amount, from, to) }
    return columns to commands
}

private data class Command(val amount: Int, val from: Int, val to: Int)

private fun interface Crane {
    fun runCommand(command: Command, columns: Crates): Crates
}

private val CrateMover9000 = Crane { (amount, from, to), columns ->
    columns.toMutableMap().apply {
        val fromCol = getOrElse(from) { throw IndexOutOfBoundsException("no entry for $from") }
        val toCol = getOrElse(to) { throw IndexOutOfBoundsException("no entry for $to") }
        set(to, toCol + fromCol.takeLast(amount).reversed())
        set(from, fromCol.dropLast(amount))
    }
}

private val CrateMover9001 = Crane { (amount, from, to), columns ->
    columns.toMutableMap().apply {
        val fromCol = getOrElse(from) { throw IndexOutOfBoundsException("no entry for $from in $this") }
        val toCol = getOrElse(to) { throw IndexOutOfBoundsException("no entry for $from in $this") }
        set(to, toCol + fromCol.takeLast(amount))
        set(from, fromCol.dropLast(amount))
    }
}