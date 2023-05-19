package com.github.leuludyha.domain.model.library.Loaded

import com.github.leuludyha.domain.model.interfaces.Keyed
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.Serializable

@Serializable
data class LoadedAuthor(
    val id: String,
    val name: String?,
    val birthDate: String?,
    val deathDate: String?,
    val wikipedia: String?,
    val works: List<LoadedWork>,
    val covers: List<Cover>,
) {

    fun toAuthor(): Author {
        return Author(
            id = id,
            name = name,
            birthDate = birthDate,
            deathDate = deathDate,
            wikipedia = wikipedia,
            works = flowOf(works.map { it.toWork() }),
            covers = flowOf(covers),
        )
    }

}