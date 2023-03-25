package com.github.leuludyha.domain.model

import kotlinx.coroutines.flow.Flow

data class Author(
    val id: String,
    val name: String?,
    val birthDate: String?,
    //val bio: String?,
    val wikipedia: String?,
    val entityType: String?,
    val photos: Flow<List<Cover>>,
) {
    override fun toString(): String = name?: "Unknown author"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Author) return false

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}