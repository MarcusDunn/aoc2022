package io.github.marcusdunn.aoc2022.day05

import io.github.marcusdunn.aoc2022.splitAt
import io.github.marcusdunn.aoc2022.transpose
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

private fun parseCrates(cratesLines: Sequence<String>) = cratesLines
    .map { line -> line.chunked(4) { it[1] }.map { if (it == ' ') null else it } }
    .toList()
    .dropLast(1)
    .transpose()
    .withIndex()
    .associate { (idx, v) -> idx + 1 to v.reversed().filterNotNull() }

private data class Command(val amount: Int, val from: Int, val to: Int)

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

private fun interface Crane {
    fun runCommand(command: Command, columns: Crates): Crates
}

private val CrateMover9000 = Crane { (amount, from, to), columns ->
    columns.mapValues { (idx, stack) ->
        when (idx) {
            from -> stack.dropLast(amount)
            to -> stack + columns[from]!!.takeLast(amount).reversed()
            else -> stack
        }
    }
}

private val CrateMover9001 = Crane { (amount, from, to), columns ->
    columns.mapValues { (idx, stack) ->
        when (idx) {
            from -> stack.dropLast(amount)
            to -> stack + columns[from]!!.takeLast(amount)
            else -> stack
        }
    }
}