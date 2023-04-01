package com.github.leuludyha.ibdb.util

import java.util.stream.Stream
import kotlin.math.max
import kotlin.streams.toList

/**
 * Compute the similarity between [this] list and
 * the [other] list
 */
fun <T> List<T>.similarity(other: List<T>): Float {
    var sum = 0.0f
    // Compute cardinality of intersection
    for (t: T in this) {
        if (other.contains(t)) {
            sum += 1
        }
    }
    // Divide by cardinality of union
    // (Which is sum of cardinalities minus cardinality of intersection)
    sum /= (this.size + other.size - sum)
    return sum
}

/** @return (a-b) / max(a,b) */
fun Number.normalizedDifference(other: Number): Float {
    return (this.toFloat() - other.toFloat()) / (max(this.toFloat(), other.toFloat()))
}

/** @return The same pair with the mapper applied to the first member of the pair */
fun <L1, R1, L2> Pair<L1, R1>.mapFirst(mapper: (L1) -> L2): Pair<L2, R1> {
    return Pair(mapper(this.first), this.second)
}

/** @return The same pair with the mapper applied to the first member of the pair */
fun <L1, R1, R2> Pair<L1, R1>.mapSecond(mapper: (R1) -> R2): Pair<L1, R2> {
    return Pair(this.first, mapper(this.second))
}

/** @return Return a map from a string */
fun <L, R> Stream<Pair<L, R>>.toMap() = this.toList().toMap()

/** @return Return a map from a string */
fun Stream<Float>.sum(): Float = this.toList().sum()

/**
 * If [this] is a map of key -> Weight, where weight is a float from 0 to +oo,
 * return the map of normalized weights : key -> weight' where weight' is a float from 0 to 1
 */
fun <K> Map<K, Float>.normalizedWeights(): Map<K, Float> {
    val sum: Float = this.values.sum()
    return this.mapValues { it.value / sum }
}