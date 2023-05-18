package com.github.leuludyha.domain.model.library.Loaded

import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Work
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.Serializable

@Serializable
data class LoadedEdition(
    val id: String,
    val title: String?,
    val isbn13: String?,
    val isbn10: String?,
    val authors: List<LoadedAuthor>,
    val works: List<LoadedWork>,
    val covers: List<Cover>,
) {

    fun toEdition(): Edition {
        return Edition(
            id = id,
            title = title,
            isbn13 = isbn13,
            isbn10 = isbn10,
            authors = flowOf(authors.map { it.toAuthor() }),
            works = flowOf(works.map { it.toWork() }),
            covers = flowOf(covers),
        )
    }

}
