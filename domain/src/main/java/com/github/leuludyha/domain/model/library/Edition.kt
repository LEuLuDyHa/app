package com.github.leuludyha.domain.model.library

import kotlinx.coroutines.flow.Flow

data class Edition(
    val id: String,
    val title: String?,
    val isbn13: String?,
    val isbn10: String?,
    val authors: Flow<List<Author>>,
    val works: Flow<List<Work>>,
    val covers: Flow<List<Cover>>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Edition) return false

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
