package com.github.leuludyha.domain.model.library

import com.github.leuludyha.domain.model.interfaces.Keyed
import com.github.leuludyha.domain.model.library.Loaded.LoadedEdition
import com.github.leuludyha.domain.model.library.Loaded.LoadedWork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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

    fun toLoadedWork(depth: Int = 3): LoadedWork {
        return runBlocking {
            if (depth > 0) {
                LoadedWork(
                    id = id,
                    title = title,
                    editions = editions.first().map { it.toLoadedEdition(depth - 1) },
                    authors = authors.first().map { it.toLoadedAuthor(depth - 1) },
                    covers = covers.first(),
                    subjects = subjects.first(),
                    nbOfPages = nbOfPages,
                )
            } else {
                LoadedWork(
                    id = id,
                    title = title,
                    editions = emptyList(),
                    authors = emptyList(),
                    covers = covers.first(),
                    subjects = subjects.first(),
                    nbOfPages = nbOfPages,
                )
            }
        }
    }

}