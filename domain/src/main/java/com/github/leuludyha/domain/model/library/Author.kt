package com.github.leuludyha.domain.model.library

import com.github.leuludyha.domain.model.interfaces.Keyed
import kotlinx.coroutines.flow.Flow

data class Author(
    val id: String,
    val name: String?,
    val birthDate: String?,
    val deathDate: String?,
    //val bio: String?,
    val wikipedia: String?,
    val works: Flow<List<Work>>,
    val covers: Flow<List<Cover>>,
): Keyed {
    override fun Id() = id
    override fun toString(): String = name?: "Unknown author"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Author) return false

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}