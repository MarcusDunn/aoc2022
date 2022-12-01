package io.github.marcusdunn.aoc2022

fun <T> Sequence<T>.splitAt(predicate: (T) -> Boolean): Sequence<List<T>> = sequence {
    val accumulator = mutableListOf<T>()
    for (element in this@splitAt) {
        if (predicate(element)) {
            yield(accumulator.toList())
            accumulator.clear()
        } else {
            accumulator.add(element)
        }
    }
    if (accumulator.isNotEmpty()) {
        yield(accumulator)
    }
}
