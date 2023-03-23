package com.github.leuludyha.domain.model

data class Work(
    val title: String?,
    val id: String,
    private val fetchAuthors: suspend () -> List<Author>?,
    val coverUrls: List<(CoverSize) -> String>?,
    val subjects: List<String>,
) {
    private var cachedAuthors: List<Author>? = null
    suspend fun authors(): List<Author>? {
        cachedAuthors?.let { cachedAuthors = fetchAuthors() }
        return cachedAuthors
    }
}