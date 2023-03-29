package com.github.leuludyha.data.repository.datasource

import com.github.leuludyha.data.RequiringLibraryDatabaseTest
import com.github.leuludyha.data.TestUtils.author1
import com.github.leuludyha.data.TestUtils.author2
import com.github.leuludyha.data.TestUtils.author3
import com.github.leuludyha.data.TestUtils.cover1
import com.github.leuludyha.data.TestUtils.cover2
import com.github.leuludyha.data.TestUtils.cover3
import com.github.leuludyha.data.TestUtils.edition1
import com.github.leuludyha.data.TestUtils.edition2
import com.github.leuludyha.data.TestUtils.edition3
import com.github.leuludyha.data.TestUtils.subject1
import com.github.leuludyha.data.TestUtils.subject2
import com.github.leuludyha.data.TestUtils.subject3
import com.github.leuludyha.data.TestUtils.work1
import com.github.leuludyha.data.TestUtils.work2
import com.github.leuludyha.data.TestUtils.work3
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Work
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LibraryLocalDataSourceTest: RequiringLibraryDatabaseTest() {

    @Test
    fun getWorkGivesExpectedResult() {
        runBlocking {
            val expected = Work(
                id = work1.workId,
                title = work1.title,
                editions = flowOf(listOf(edition1, edition2, edition3).map { it.toModel(libraryDao) }),
                authors = flowOf(listOf(author1, author2, author3).map { it.toModel(libraryDao) }),
                covers = flowOf(listOf(cover1, cover2, cover3).map { it.toModel(libraryDao) }),
                subjects = flowOf(listOf(subject1, subject2, subject3).map { it.toModel(libraryDao) }),
            )
            val result = localDataSource.getWork(work1.workId).first()
            assertThat(result).isEqualTo(expected)
            assertThat(result.authors.first()).isEqualTo(expected.authors.first())
            assertThat(result.covers.first()).isEqualTo(expected.covers.first())
            assertThat(result.editions.first()).isEqualTo(expected.editions.first())
            assertThat(result.subjects.first()).isEqualTo(expected.subjects.first())
        }
    }

    @Test
    fun getEditionGivesExpectedResult() {
        runBlocking {
            val expected = Edition(
                id = edition1.editionId,
                title = edition1.title,
                isbn13 = edition1.isbn13,
                isbn10 = edition1.isbn10,
                works = flowOf(listOf(work1, work2, work3).map { it.toModel(libraryDao) }),
                authors = flowOf(listOf(author1, author2, author3).map { it.toModel(libraryDao) }),
                covers = flowOf(listOf(cover1, cover2, cover3).map { it.toModel(libraryDao) }),
            )
            val result = localDataSource.getEdition(edition1.editionId).first()
            assertThat(result).isEqualTo(expected)
            assertThat(result.authors.first()).isEqualTo(expected.authors.first())
            assertThat(result.covers.first()).isEqualTo(expected.covers.first())
            assertThat(result.works.first()).isEqualTo(expected.works.first())
        }
    }

    @Test
    fun getAuthorGivesExpectedResult() {
        runBlocking {
            val expected = Author(
                id = author1.authorId,
                name = author1.name,
                birthDate = author1.birthDate,
                deathDate = author1.deathDate,
                //bio = author1.bio,
                wikipedia = author1.wikipedia,
                works = flowOf(listOf(work1, work3).map{it.toModel(libraryDao)}),
                photos = flowOf(listOf(cover1, cover2, cover3).map { it.toModel(libraryDao) }),
            )
            val result = localDataSource.getAuthor(author1.authorId).first()
            assertThat(result).isEqualTo(expected)
            assertThat(result.name).isEqualTo(expected.name)
            //assertThat(result.bio).isEqualTo(expected.bio)
            assertThat(result.wikipedia).isEqualTo(expected.wikipedia)
            assertThat(result.birthDate).isEqualTo(expected.birthDate)
            assertThat(result.deathDate).isEqualTo(expected.deathDate)
            assertThat(result.photos.first()).isEqualTo(expected.photos.first())
        }
    }

    @Test
    fun getCoverGivesExpectedResult() {
        runBlocking {
            val expected = Cover(id = 1)
            val result = localDataSource.getCover(cover1.coverId).first()
            assertThat(result).isEqualTo(expected)
        }
    }
}