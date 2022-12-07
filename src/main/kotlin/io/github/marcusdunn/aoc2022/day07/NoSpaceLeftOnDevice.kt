package io.github.marcusdunn.aoc2022.day07

import java.nio.file.Path
import kotlin.io.path.readText

fun part1(path: Path) = parseRoot(path)
    .fold(0) { acc, file ->
        when (file) {
            is File.Directory -> if (file.size() < 100000) acc + file.size() else acc
            is File.Regular -> acc
        }
    }

fun part2(path: Path) = parseRoot(path)
    .fold(listOf<File.Directory>()) { acc, file ->
        when (file) {
            is File.Directory -> acc + file
            else -> acc
        }
    }
    .sortedBy { it.size() }
    .let { queue ->
        val root = queue.last()
        val unused = 70_000_000 - root.size()
        queue.first { unused + it.size() >= 30_000_000 }
    }.size()

private fun parseRoot(path: Path) = path
    .readText()
    .splitToSequence("$")
    .drop(1)
    .map { it.trim() }
    .drop(1)
    .map { it.lines() }
    .map { it.first() to it.drop(1) }
    .onEach { println(it) }
    .fold(File.Directory("/", mutableListOf(), null)) { pwd, (commandString, out) ->
        when (val command = Command.parse(commandString)) {
            is Command.Cd -> when (command.path) {
                ".." -> pwd.parent ?: pwd
                else -> (pwd.contents.find { it.name == command.path } ?: File.Directory(
                    command.path,
                    mutableListOf(),
                    pwd
                ))
                        as File.Directory
            }
            is Command.Ls -> pwd.apply { contents.addAll(out.map { File.parse(it, pwd) }) }
        }
    }
    .root()

private sealed class Command {
    data class Cd(val path: String) : Command()
    object Ls : Command()
    companion object
}

private fun Command.Companion.parse(line: String): Command {
    val split = line.split(" ", limit = 2)
    return when (split.first()) {
        "cd" -> Command.Cd(split.last())
        "ls" -> Command.Ls
        else -> throw IllegalArgumentException("Unknown command. line: $line")
    }
}

private sealed class File {
    abstract val name: String
    abstract val parent: Directory?

    abstract fun size(): Int

    abstract fun <T> fold(init: T, visitor: (T, File) -> T): T

    data class Directory(override val name: String, val contents: MutableList<File>, override val parent: Directory?) :
        File() {
        fun root(): Directory = parent?.root() ?: this
        override fun size() = contents.sumOf { it.size() }
        override fun <T> fold(init: T, visitor: (T, File) -> T): T =
            contents.fold(visitor(init, this)) { acc, file -> file.fold(acc, visitor) }


        override fun toString(): String {
            return "Directory(name='$name', contents=$contents, parent=${parent?.name})"
        }
    }

    data class Regular(override val name: String, val size: Int, override val parent: Directory?) : File() {
        override fun size() = size
        override fun <T> fold(init: T, visitor: (T, File) -> T): T = visitor(init, this)

        override fun toString(): String {
            return "Regular(name='$name', size=$size, parent=${parent?.name})"
        }
    }

    companion object
}

private fun File.Companion.parse(line: String, parent: File.Directory): File {
    val (discriminant, rest) = line.split(" ", limit = 2)
    return when (discriminant) {
        "dir" -> File.Directory(rest, mutableListOf(), parent)
        else -> File.Regular(rest, discriminant.toInt(), parent)
    }
}