package com.github.leuludyha.domain.model

// We might want to improve this in the future but I figured this is the minimum we need
data class Author(
    val id: String?,
    val wikipedia: String?,
    val name: String?,
    val photoIds: List<Int>?,
    val bio: String?,
    val entityType: String?,
)

fun formatListToText(authors: List<Author>): String {
    if (authors.size == 0) {
        return ""; }
    if (authors.size == 1) {
        return authors[0].name.orEmpty(); }
    if (authors.size == 2) {
        return "${authors[0].name.orEmpty()} and ${authors[1].name.orEmpty()}"
    }

    val sb = StringBuilder()
    authors.forEachIndexed { i, author ->
        sb.append(author.name.orEmpty())

        if (i < authors.size - 2) {
            sb.append(", ")
        } else if (i == authors.size - 2) {
            sb.append(" and ")
        }
    }
    return sb.toString()
}