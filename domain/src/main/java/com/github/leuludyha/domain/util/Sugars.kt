package com.github.leuludyha.ibdb.util

import kotlin.math.max

/**
 * Compute the similarity between [this] list and
 * the [other] list
 */
fun <T> List<T>.similarity(other: List<T>): Float {
    if (this.isEmpty() and other.isEmpty()) {
        return 0.0f; }
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
    if (this.toFloat() == other.toFloat()) {
        return 0.0f; }
    return (this.toFloat() - other.toFloat()) / (max(this.toFloat(), other.toFloat()))
}

/**
 * If [this] is a map of key -> Weight, where weight is a float from 0 to +oo,
 * return the map of normalized weights : key -> weight' where weight' is a float from 0 to 1
 */
fun <K> Map<K, Float>.normalizedWeights(): Map<K, Float> {
    val sum: Float = this.values.sum()
    return this.mapValues { it.value / sum }
}