package com.github.leuludyha.domain.model

data class Edition(
    val id: String?,
    val title: String?,
    private val fetchAuthors: suspend () -> List<Author>?,
    private val fetchWorks: suspend () -> List<Work>?,
    val coverUrls: List<(CoverSize) -> String>?,
) {
    private var cachedAuthors: List<Author>? = null
    private var cachedWorks: List<Work>? = null
    suspend fun authors(): List<Author>? {
        cachedAuthors?.let { cachedAuthors = fetchAuthors() }
        return cachedAuthors
    }
    suspend fun works(): List<Work>? {
        cachedWorks?.let { cachedWorks = fetchWorks() }
        return cachedWorks
    }
}
