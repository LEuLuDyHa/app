package com.github.leuludyha.domain.model

data class Work(
    var pk: Long = 0,
    val title: String?,
    val id: String?,
    val authorIds: List<String>?,
    val covers: List<Cover>?,
    val id: String,
    private val fetchAuthors: suspend () -> List<Author>?,
    val coverUrls: List<(CoverSize) -> String>?,
    val subjects: List<String>?,
)