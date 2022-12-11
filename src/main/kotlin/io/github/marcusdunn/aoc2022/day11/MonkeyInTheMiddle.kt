package io.github.marcusdunn.aoc2022.day11

import io.github.marcusdunn.aoc2022.splitAt
import java.nio.file.Path
import kotlin.io.path.useLines
import kotlin.math.floor


val ROUNDS = 20
fun part1(path: Path) = path.useLines { lines ->
    (1..ROUNDS)
        .fold(lines
            .splitAt { it.isBlank() }
            .map { parseMonkey(it) }
            .toList()
            .sortedBy { it.id }
        ) { it, i -> it.doRound().also { println("round: $i\n" + it.joinToString("\n") { it.status() }) } }
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
        ?.let { floor(it / 3.0).toInt() }
        ?: return this

    val passTo = if (item % monkey.dividableBy == 0) {
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

fun lcm(a: Int, b: Int): Int = (a * b) / gcd(a, b)

tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

data class Monkey(
    val id: Int,
    val items: List<Int>,
    val op: (Int) -> Int,
    val dividableBy: Int,
    val onTruePassTo: Int,
    val onFalsePassTo: Int,
    val itemsInspected: Int,
) {
    companion object
}

fun Monkey.status(): String = "Monkey $id: ${items.joinToString()}"


fun parseMonkey(lines: Sequence<String>): Monkey {
    val toList = lines.toList()
    val iterator = toList.iterator()
    return Monkey(
        id = iterator.next().removePrefix("Monkey ").removeSuffix(":").toInt(),
        items = parseItems(iterator.next().replaceBefore(":", "").removePrefix(":").trim()),
        op = parseOp(iterator.next().replaceBefore(":", "").removePrefix(":").trim()),
        dividableBy = parseTest(iterator.next().replaceBefore(":", "").removePrefix(":").trim()),
        onTruePassTo = parseAction(iterator.next().replaceBefore(":", "").removePrefix(":").trim()),
        onFalsePassTo = parseAction(iterator.next().replaceBefore(":", "").removePrefix(":").trim()),
        itemsInspected = 0,
    )
}

private fun parseItems(it: String) = it.trim().split(" ").map { it.replace(",", "").toInt() }

private fun parseOp(it: String): (Int) -> Int {
    return it.trim().split(" ").let { (_, _, _, op, y) ->
        val calcRhs: (Int) -> Int = when (y) {
            "old" -> ({ it })
            else -> ({ y.toInt() })
        }
        val combine: (Int, Int) -> Int = when (op) {
            "*" -> Int::times
            "+" -> Int::plus
            "-" -> Int::minus
            else -> throw IllegalArgumentException("$op not recognized")
        }
        { old: Int -> combine(old, calcRhs(old)) }
    }
}

private fun parseTest(string: String): Int = if (string.startsWith("divisible by")) {
    string.removePrefix("divisible by ").toInt()
} else throw IllegalArgumentException("$string does not start with \"divisible by\"")

private fun parseAction(string: String): Int = if (string.startsWith("throw to monkey ")) {
    string.removePrefix("throw to monkey ").toInt()
} else throw IllegalArgumentException("$string does not start with \"throw to monkey\"")

