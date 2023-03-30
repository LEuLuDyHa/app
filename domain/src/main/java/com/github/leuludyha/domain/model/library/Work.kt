package com.github.leuludyha.domain.model.library

import com.github.leuludyha.domain.model.interfaces.Keyed

import kotlinx.coroutines.flow.Flow

// TODO add description, if possible
data class Work(
    val id: String,
    val title: String?,
    val editions: Flow<List<Edition>>,
    val authors: Flow<List<Author>>,
    val covers: Flow<List<Cover>>,
    val subjects: Flow<List<String>>,
): Keyed {
    override fun Id() = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Work) return false

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}