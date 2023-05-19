package com.github.leuludyha.domain.model.library

import com.github.leuludyha.domain.model.interfaces.Keyed
import com.github.leuludyha.domain.model.library.Loaded.LoadedWork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

// TODO add description, if possible
data class Work(
    val id: String,
    val title: String?,
    val editions: Flow<List<Edition>>,
    val authors: Flow<List<Author>>,
    val covers: Flow<List<Cover>>,
    val subjects: Flow<List<String>>,
    val nbOfPages: Int = 0,
): Keyed {
    override fun Id() = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Work) return false

        return id == other.id
    }

    override fun toString(): String {
        return "${this.title}"
    }

    override fun hashCode(): Int = id.hashCode()

    fun toLoadedWork(): LoadedWork {
        return runBlocking {
             LoadedWork(
                id = id,
                title = title,
                editions = editions.firstOrNull()?.map { it.toLoadedEdition() } ?: emptyList(),
                authors = authors.firstOrNull()?.map { it.toLoadedAuthor() } ?: emptyList(),
                covers = covers.firstOrNull() ?: emptyList(),
                subjects = subjects.firstOrNull() ?: emptyList(),
                nbOfPages = nbOfPages,
            )
        }
    }

}