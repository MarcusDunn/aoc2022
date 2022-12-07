package io.github.marcusdunn.aoc2022.day07

import java.nio.file.Path
import kotlin.io.path.readText
import kotlin.math.min

fun part1(path: Path) = parseRoot(path)
    .fold(0) { acc, file ->
        when (file) {
            is File.Directory -> if (file.size() < 100_000) acc + file.size() else acc
            is File.Regular -> acc
        }
    }

fun part2(path: Path): Int {
    val root = parseRoot(path)
    val unused = 70_000_000 - root.size()
    return root.fold(Int.MAX_VALUE) { acc, file ->
        when (file) {
            is File.Directory -> {
                val size = file.size()
                if (unused + size >= 30_000_000) min(acc, size) else acc
            }

            is File.Regular -> acc
        }
    }
}

private fun parseRoot(path: Path) = path
    .readText()
    .splitToSequence("$")
    .drop(2)
    .map { it.trim() }
    .map { it.lines() }
    .map { Command.parse(it.first()) to it.drop(1) }
    .fold(File.Directory("/", mutableListOf(), null)) { wd, (command, out) ->
        when (command) {
            is Command.Cd -> when (command.path) {
                ".." -> wd.parent ?: wd
                else -> wd.contents
                    .filterIsInstance<File.Directory>()
                    .find { it.name == command.path }!!
            }
            is Command.Ls -> wd.apply { contents.addAll(out.map { File.parse(it, wd) }) }
        }
    }
    .root()

private sealed class Command {
    data class Cd(val path: String) : Command()
    object Ls : Command()
    companion object {
        fun parse(command: String) = when {
            command.startsWith("cd ") -> Cd(command.substring(3))
            command == "ls" -> Ls
            else -> error("Unknown command: $command")
        }
    }
}

private sealed class File {
    abstract val name: String
    abstract val parent: Directory?

    fun root(): Directory = parent?.root() ?: this as Directory
    abstract fun size(): Int
    abstract fun <T> fold(init: T, visitor: (T, File) -> T): T

    class Directory(override val name: String, val contents: MutableList<File>, override val parent: Directory?) :
        File() {
        override fun size() = contents.sumOf { it.size() }
        override fun <T> fold(init: T, visitor: (T, File) -> T): T =
            contents.fold(visitor(init, this)) { acc, file -> file.fold(acc, visitor) }
    }

    class Regular(override val name: String, val size: Int, override val parent: Directory) : File() {
        override fun size() = size
        override fun <T> fold(init: T, visitor: (T, File) -> T): T = visitor(init, this)
    }

    companion object {
        fun parse(line: String, parent: Directory) = when {
            line.startsWith("dir") -> Directory(line.substringAfter("dir "), mutableListOf(), parent)
            else -> line.split(" ").let { (size, name) -> Regular(name, size.toInt(), parent) }
        }
    }
}