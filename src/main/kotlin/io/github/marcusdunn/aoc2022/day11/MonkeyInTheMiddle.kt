package io.github.marcusdunn.aoc2022.day11

import io.github.marcusdunn.aoc2022.splitAt
import java.nio.file.Path
import kotlin.io.path.useLines


val ROUNDS = 10_000

fun part2(path: Path) = path.useLines { lines ->
    (1..ROUNDS)
        .fold(lines
            .splitAt { it.isBlank() }
            .map { parseMonkey(it) }
            .toList()
            .sortedBy { it.id }
        ) { it, i ->
            it.doRound()
        }
        .sortedByDescending { it.itemsInspected }
        .take(2)
        .onEach { println(it.itemsInspected) }
        .fold(1L) { acc, x -> acc * x.itemsInspected }
}

private fun List<Monkey>.doRound() = map { it.id }
    .fold(this) { acc, id ->
        acc.turn(acc.find { it.id == id }!!)
    }

fun List<Monkey>.turn(monkey: Monkey): List<Monkey> {
    val item = monkey.items
        .firstOrNull()
        ?.let(monkey.op)
        ?.let { it % this.map { it.testDivisor }.reduce(::lcm) }
        ?: return this

    val passTo = if (item % monkey.testDivisor == 0L) {
        monkey.onTruePassTo
    } else {
        monkey.onFalsePassTo
    }
    return this.map {
        when (it.id) {
            passTo -> it.copy(items = it.items + item)
            monkey.id -> it.copy(items = it.items.drop(1), itemsInspected = it.itemsInspected + 1)
            else -> it
        }
    }.let { it.turn(it.find { it.id == monkey.id }!!) }
}

fun lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)

tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

data class Monkey(
    val id: Long,
    val items: List<Long>,
    val op: (Long) -> Long,
    val testDivisor: Long,
    val onTruePassTo: Long,
    val onFalsePassTo: Long,
    val itemsInspected: Int,
) {
    companion object
}

fun Monkey.status(): String = "Monkey $id: $itemsInspected"


fun parseMonkey(lines: Sequence<String>): Monkey {
    val toList = lines.toList()
    val iterator = toList.iterator()
    return Monkey(
        id = iterator.next().removePrefix("Monkey ").removeSuffix(":").toLong(),
        items = parseItems(iterator.next().replaceBefore(":", "").removePrefix(":").trim()),
        op = parseOp(iterator.next().replaceBefore(":", "").removePrefix(":").trim()),
        testDivisor = parseTest(iterator.next().replaceBefore(":", "").removePrefix(":").trim()),
        onTruePassTo = parseAction(iterator.next().replaceBefore(":", "").removePrefix(":").trim()),
        onFalsePassTo = parseAction(iterator.next().replaceBefore(":", "").removePrefix(":").trim()),
        itemsInspected = 0,
    )
}

private fun parseItems(it: String) = it.trim().split(" ").map { it.replace(",", "").toLong() }

private fun parseOp(it: String): (Long) -> Long {
    return it.trim().split(" ").let { (_, _, _, op, y) ->
        val calcRhs: (Long) -> Long = when (y) {
            "old" -> ({ it })
            else -> ({ y.toLong() })
        }
        val combine: (Long, Long) -> Long = when (op) {
            "*" -> Long::times
            "+" -> Long::plus
            else -> throw IllegalArgumentException("$op not recognized")
        }
        { old: Long -> combine(old, calcRhs(old)) }
    }
}

private fun parseTest(string: String) = if (string.startsWith("divisible by")) {
    string.removePrefix("divisible by ").toLong()
} else throw IllegalArgumentException("$string does not start with \"divisible by\"")

private fun parseAction(string: String)= if (string.startsWith("throw to monkey ")) {
    string.removePrefix("throw to monkey ").toLong()
} else throw IllegalArgumentException("$string does not start with \"throw to monkey\"")

