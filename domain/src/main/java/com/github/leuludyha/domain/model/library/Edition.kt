package com.github.leuludyha.domain.model.library

import com.github.leuludyha.domain.model.library.Loaded.LoadedEdition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

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

    fun toLoadedEdition(): LoadedEdition {
        return runBlocking {
            LoadedEdition(
                id = id,
                title = title,
                isbn13 = isbn13,
                isbn10 = isbn10,
                authors = authors.firstOrNull()?.map { it.toLoadedAuthor() } ?: emptyList(),
                works = works.firstOrNull()?.map { it.toLoadedWork() } ?: emptyList(),
                covers = covers.first()
            )
        }
    }

}
