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
        .map { (move, from, to) -> crane.createCommand(move, from, to) }
        .fold(columns) { acc, command -> command.execute(acc) }
        .values
        .map { it.last() }
        .joinToString(separator = "")
}

fun interface Crane {
    fun createCommand(amount: Int, from: Int, to: Int): Command
}

fun interface Command {
    fun execute(columns: Map<Int, List<Char>>): Map<Int, List<Char>>
}

object CrateMover9000 : Crane {
    override fun createCommand(amount: Int, from: Int, to: Int) = Command { columns ->
        columns.toMutableMap().apply {
            val fromCol = get(from)!!
            val toCol = get(to)!!
            set(to, toCol + fromCol.takeLast(amount).reversed())
            set(from, fromCol.dropLast(amount))
        }
    }
}

object CrateMover9001 : Crane {
    override fun createCommand(amount: Int, from: Int, to: Int) = Command { columns ->
        columns.toMutableMap().apply {
            val fromCol = get(from)!!
            val toCol = get(to)!!
            set(to, toCol + fromCol.takeLast(amount))
            set(from, fromCol.dropLast(amount))
        }
    }
}