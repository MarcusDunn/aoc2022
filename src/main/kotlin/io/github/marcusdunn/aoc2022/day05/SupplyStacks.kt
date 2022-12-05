package io.github.marcusdunn.aoc2022.day05

import io.github.marcusdunn.aoc2022.splitAt
import java.nio.file.Path
import kotlin.io.path.useLines

fun part1(path: Path) = solve(CrateMover9000, path)
fun part2(path: Path) = solve(CrateMover9001, path)
private fun solve(crane: Crane, path: Path) = parse(path) { crates, commands -> run(crane, crates, commands) }

private typealias Crates = Map<Int, List<Char>>

private fun run(crane: Crane, crates: Crates, commands: Sequence<Command>) = commands
    .fold(crates) { acc, command -> crane.runCommand(command, acc) }
    .values
    .map { it.last() }
    .joinToString(separator = "")

private fun <T> parse(path: Path, block: (crates: Crates, commands: Sequence<Command>) -> T) = path.useLines { lines ->
    lines
        .splitAt { it.isBlank() }
        .iterator()
        .run { block(parseCrates(next()), parseCommands(next())) }
}

private fun parseCrates(cratesLines: Sequence<String>): Crates {
    val rowsWithIndex = cratesLines
        .map { line ->
            line
                .chunked(4) { it[1] }
                .map { if (it == ' ') null else it }
        }
        .toList()
    val rows = rowsWithIndex.dropLast(1)
    return rowsWithIndex
        .last()
        .map { it ?: throw NullPointerException("last row contained null values") }
        .map { it.digitToInt() }
        .associateWith { idx ->
            rows
                .map { it.getOrNull(idx - 1) }
                .reversed()
                .filterNotNull()
        }
}

private val COMMAND_REGEX = Regex("""move (\d+) from (\d+) to (\d+)""")
private fun parseCommands(commands: Sequence<String>) = commands
    .map { COMMAND_REGEX.find(it) ?: throw IllegalArgumentException("invalid command $it") }
    .map { matchResult ->
        matchResult
            .destructured
            .toList()
            .map { it.toInt() }
    }
    .map { (amount, from, to) -> Command(amount, from, to) }

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