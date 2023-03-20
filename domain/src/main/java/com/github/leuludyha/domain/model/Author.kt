package com.github.leuludyha.domain.model

// We might want to improve this in the future but I figured this is the minimum we need
data class Author(
    var pk: Long = 0,
    val wikipedia: String?,
    val name: String?,
    val id: String?,
    val photoIds: List<Int>?,
    val bio: String?,
    val entityType: String?,
)