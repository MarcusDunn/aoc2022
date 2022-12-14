package io.github.marcusdunn.aoc2022.day10

import java.nio.file.Path
import kotlin.io.path.useLines

fun part1(path: Path) = path.useLines { lines ->
    lines
        .toInstructions()
        .toStates()
        .filter { it.pc >= 20 }
        .filter { it.pc - 20 % 40 == 0 }
        .sumOf { it.pc * it.x }
}

private const val CRT_ROWS = 6
private const val CRT_COLUMNS = 40
private const val NEWLINE_SIZE = 1

fun part2(path: Path) = path.useLines { lines ->
    lines
        .toInstructions()
        .toStates()
        .withIndex()
        .fold(StringBuilder(CRT_ROWS * (CRT_COLUMNS + NEWLINE_SIZE))) { stringBuilder, (i, state) ->
            val crtLoc = i % 40
            stringBuilder.apply {
                if (crtLoc == 0 && i != 0) {
                    append('\n')
                }
                if (crtLoc - state.x in -1..1) {
                    append('█')
                } else {
                    append(' ')
                }
            }
        }
        .toString()
}

private fun Sequence<Instruction>.toStates() = sequence {
    var curr = State(1, 1)
    for (instruction in this@toStates) {
        yield(curr)
        curr = State(curr.pc + 1, instruction.apply(curr.x))
    }
}

private data class State(val pc: Int, val x: Int)

private fun Sequence<String>.toInstructions() = this
    .map { Instruction.parse(it) }
    .flatMap { List(it.cycles - 1) { Instruction.Noop } + it }

private fun Instruction.apply(target: Int) = when (this) {
    is Instruction.AddX -> target + x
    Instruction.Noop -> target
}

private sealed class Instruction(val cycles: Int) {
    data class AddX(val x: Int) : Instruction(2)
    object Noop : Instruction(1)

    companion object {
        fun parse(line: String) = if (line == "noop") {
            Noop
        } else if (line.startsWith("addx")) {
            AddX(line.split(" ").last().toInt())
        } else {
            throw IllegalArgumentException("no instruction for $line")
        }
    }
}