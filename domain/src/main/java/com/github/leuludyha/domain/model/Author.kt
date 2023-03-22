package com.github.leuludyha.domain.model

import kotlinx.coroutines.flow.Flow

// We might want to improve this in the future but I figured this is the minimum we need
data class Author(
    val id: String,
    val name: String?,
    val bio: String?,
    val photos: Flow<List<Cover>>,
    val wikipedia: String?,
    val entityType: String?,
)