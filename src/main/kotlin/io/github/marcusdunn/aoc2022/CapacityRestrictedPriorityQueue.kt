package io.github.marcusdunn.aoc2022

import java.util.PriorityQueue
import java.util.Queue

class CapacityRestrictedPriorityQueue<T : Any> private constructor(
    private val capacity: Int,
    private val comparator: Comparator<T>,
    private val inner: PriorityQueue<T>,
) : Queue<T> by inner {
    constructor(
        capacity: Int,
        comparator: Comparator<T>
    ) : this(capacity, comparator, PriorityQueue(capacity, comparator))

    companion object {
        operator fun <T : Comparable<T>> Companion.invoke(capacity: Int) =
            CapacityRestrictedPriorityQueue<T>(capacity, naturalOrder())
    }

    override fun add(element: T) = offer(element)
    override fun offer(e: T) = if (size < capacity) {
        inner.offer(e)
    } else {
        val head = peek()
        if (comparator.compare(e, head) > 0) {
            poll()
            inner.offer(e)
        } else {
            false
        }
    }
}