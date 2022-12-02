package io.github.marcusdunn.aoc2022

fun <T> Sequence<T>.splitAt(predicate: (T) -> Boolean): Sequence<Sequence<T>> = sequence {
    val iterator = iterator()
    while (iterator.hasNext()) {
        yield(sequence {
            while (iterator.hasNext()) {
                val next = iterator.next()
                if (predicate(next)) {
                    break
                }
                yield(next)
            }
        })
    }
}