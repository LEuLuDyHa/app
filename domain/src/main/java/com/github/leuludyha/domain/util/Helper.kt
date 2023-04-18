package com.github.leuludyha.domain.util

import androidx.compose.ui.graphics.Color

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

/**
 * Interpolate between two colors
 * @param that Other color to use
 * @param factor The amount of [this] color compared to [that] color
 */
fun Color.mix(that: Color, factor: Float): Color {
    return Color(
        this.red * factor + that.red * (1 - factor),
        this.green * factor + that.green * (1 - factor),
        this.blue * factor + that.blue * (1 - factor),
        this.alpha * factor + that.alpha * (1 - factor),
    )
}