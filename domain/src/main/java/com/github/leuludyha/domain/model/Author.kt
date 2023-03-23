package com.github.leuludyha.domain.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count

data class Author(
    val id: String,
    val name: String?,
    val bio: String?,
    val wikipedia: String?,
    val entityType: String?,
    val photos: Flow<List<Cover>>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Author) return false

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

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
}