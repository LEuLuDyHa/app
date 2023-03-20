package com.github.leuludyha.domain.model

// We might want to improve this in the future but I figured this is the minimum we need
data class Author(
    val id: String?,
    val wikipedia: String?,
    val name: String?,
    val photoIds: List<Int>?,
    val bio: String?,
    val entityType: String?,
)