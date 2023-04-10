package com.github.leuludyha.data

import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Work
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockLibraryLocalDataSourceImpl: LibraryLocalDataSource {
    private val savedWorks: HashMap<String, Work> = hashMapOf()
    private val savedEditions: HashMap<String, Edition> = hashMapOf()
    private val savedAuthors: HashMap<String, Author> = hashMapOf()

    override fun getWork(workId: String): Flow<Work> =
        if (savedWorks[workId] != null)
            flowOf(savedWorks[workId]!!)
        else
            flowOf()

    override fun getEdition(editionId: String): Flow<Edition> =
        if (savedEditions[editionId] != null)
            flowOf(savedEditions[editionId]!!)
        else
            flowOf()

    override fun getEditionByISBN(isbn: String): Flow<Edition> {
        val editions = savedEditions.values.filter { it.isbn10 == isbn || it.isbn13 == isbn }
        return if(editions.firstOrNull() == null)
            flowOf()
        else
            flowOf(editions.first())
    }

    override fun getAuthor(authorId: String): Flow<Author> =
        if (savedAuthors[authorId] != null)
            flowOf(savedAuthors[authorId]!!)
        else
            flowOf()

    override fun getCover(coverId: Long): Flow<Cover> = flowOf(Cover(coverId))

    override suspend fun saveWork(work: Work) {
        savedWorks[work.id] = work
    }

    override suspend fun saveAuthor(author: Author) {
        savedAuthors[author.id] = author
    }

    override suspend fun saveEdition(edition: Edition) {
        savedEditions[edition.id] = edition
    }
}