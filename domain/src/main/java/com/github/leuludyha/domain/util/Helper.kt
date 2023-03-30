package com.github.leuludyha.domain.util

fun <T> List<T>?.toText(): String {
    val items = this
    if (items.isNullOrEmpty()) {
        return ""; }
    if (items.size == 1) {
        return items[0].toString(); }
    if (items.size == 2) {
        return "${items[0].toString()} and ${items[1].toString()}"
    }

    val sb = StringBuilder()
    items.forEachIndexed { i, author ->
        sb.append(author.toString())

        if (i < items.size - 2) {
            sb.append(", ")
        } else if (i == items.size - 2) {
            sb.append(" and ")
        }
    }
    return sb.toString()
}

class Helper {
    fun <T> List<T>?.toText(): String {
        val items = this
        if (items.isNullOrEmpty()) {
            return ""; }
        if (items.size == 1) {
            return items[0].toString(); }
        if (items.size == 2) {
            return "${items[0].toString()} and ${items[1].toString()}"
        }

        val sb = StringBuilder()
        items.forEachIndexed { i, author ->
            sb.append(author.toString())

            if (i < items.size - 2) {
                sb.append(", ")
            } else if (i == items.size - 2) {
                sb.append(" and ")
            }
        }
        return sb.toString()
    }
}