package com.github.leuludyha.data.repository.datasource

import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Cover
import com.github.leuludyha.domain.model.Edition
import com.github.leuludyha.domain.model.Work
import kotlinx.coroutines.flow.Flow

interface LibraryLocalDataSource {
    fun getWork(workId: String): Flow<Work>
    fun getEdition(editionId: String): Flow<Edition>
    fun getAuthor(authorId: String): Flow<Author>
    fun getCover(coverId: Long): Flow<Cover>
}