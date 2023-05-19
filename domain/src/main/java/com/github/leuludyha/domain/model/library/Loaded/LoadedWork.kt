package com.github.leuludyha.domain.model.library.Loaded

import com.github.leuludyha.domain.model.interfaces.Keyed
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Work
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.Serializable
import java.util.concurrent.Flow

// TODO move to a different package
@Serializable
data class LoadedWork(
    val id: String,
    val title: String?,
    val editions: List<LoadedEdition>,
    val authors: List<LoadedAuthor>,
    val covers: List<Cover>,
    val subjects: List<String>,
    val nbOfPages: Int = 0,
) {

    fun toWork(): Work {
        return Work(
            id = id,
            title = title,
            editions = flowOf(editions.map { it.toEdition() }),
            authors = flowOf(authors.map { it.toAuthor() }),
            covers = flowOf(covers),
            subjects = flowOf(subjects),
            nbOfPages = nbOfPages,
        )
    }

}