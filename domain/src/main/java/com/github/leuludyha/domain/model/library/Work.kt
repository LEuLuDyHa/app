package com.github.leuludyha.domain.model.library

import com.github.leuludyha.domain.model.interfaces.Keyed

data class Work(
    val title: String?,
    private val id: String,
    private val fetchAuthors: suspend () -> List<Author>?,
    val coverUrls: List<(CoverSize) -> String>,
    val subjects: List<String>,
) : Keyed {
    private var cachedAuthors: List<Author>? = null
    suspend fun authors(): List<Author>? {
        cachedAuthors?.let { cachedAuthors = fetchAuthors() }
        return cachedAuthors
    }

    override fun getId(): String {
        return this.id
    }
}