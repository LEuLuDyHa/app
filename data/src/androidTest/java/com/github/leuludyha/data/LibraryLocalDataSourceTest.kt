package com.github.leuludyha.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.leuludyha.data.db.*
import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.data.repository.datasourceImpl.LibraryLocalDataSourceImpl
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Cover
import com.github.leuludyha.domain.model.Edition
import com.github.leuludyha.domain.model.Work
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import okhttp3.internal.toImmutableList
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.math.exp

class LibraryLocalDataSourceTest {
    private lateinit var db: LibraryDatabase
    private lateinit var libraryDao: LibraryDao
    private lateinit var localDataSource: LibraryLocalDataSource

    private val work1 = WorkEntity("work1", "MyWork1")
    private val work2 = WorkEntity("work2", "MyWork2")
    private val work3 = WorkEntity("work3", "MyWork3")
    private val author1 = AuthorEntity("author1", "wiki1", "Author1", "bio1", "person")
    private val author2 = AuthorEntity("author2", null, "Author2", null, null)
    private val author3 = AuthorEntity("author3", null, "Author3", null, null)
    private val author4 = AuthorEntity("author4", null, "Author4", null, null)
    private val edition1 = EditionEntity("edition1", "MyEdition1")
    private val edition2 = EditionEntity("edition2", "MyEdition2")
    private val edition3 = EditionEntity("edition3", "MyEdition3")
    private val edition4 = EditionEntity("edition4", "MyEdition4")
    private val cover1 = CoverEntity(1)
    private val cover2 = CoverEntity(2)
    private val cover3 = CoverEntity(3)
    private val subject1 = SubjectEntity("subject1")
    private val subject2 = SubjectEntity("subject2")
    private val subject3 = SubjectEntity("subject3")

    @Before
    fun createDatabase() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LibraryDatabase::class.java
        ).allowMainThreadQueries().build()
        libraryDao = db.libraryDao()
        localDataSource = LibraryLocalDataSourceImpl(libraryDao)

        populateDatabase()
    }

    private fun populateDatabase() {
        runBlocking {
            libraryDao.insert(work1)
            libraryDao.insert(work2)
            libraryDao.insert(work3)

            libraryDao.insert(author1)
            libraryDao.insert(author2)
            libraryDao.insert(author3)
            libraryDao.insert(author4)

            libraryDao.insert(edition1)
            libraryDao.insert(edition2)
            libraryDao.insert(edition3)
            libraryDao.insert(edition4)

            libraryDao.insert(cover1)
            libraryDao.insert(cover2)
            libraryDao.insert(cover3)

            libraryDao.insert(subject1)
            libraryDao.insert(subject2)
            libraryDao.insert(subject3)

            libraryDao.insert(WorkAuthorCrossRef(work1.workId, author1.authorId))
            libraryDao.insert(WorkAuthorCrossRef(work1.workId, author2.authorId))
            libraryDao.insert(WorkAuthorCrossRef(work1.workId, author3.authorId))
            libraryDao.insert(WorkAuthorCrossRef(work3.workId, author1.authorId))

            libraryDao.insert(WorkEditionCrossRef(work1.workId, edition1.editionId))
            libraryDao.insert(WorkEditionCrossRef(work1.workId, edition2.editionId))
            libraryDao.insert(WorkEditionCrossRef(work1.workId, edition3.editionId))
            libraryDao.insert(WorkEditionCrossRef(work2.workId, edition1.editionId))
            libraryDao.insert(WorkEditionCrossRef(work3.workId, edition1.editionId))

            libraryDao.insert(WorkCoverCrossRef(work1.workId, cover1.coverId))
            libraryDao.insert(WorkCoverCrossRef(work1.workId, cover2.coverId))
            libraryDao.insert(WorkCoverCrossRef(work1.workId, cover3.coverId))

            libraryDao.insert(WorkSubjectCrossRef(work1.workId, subject1.subjectName))
            libraryDao.insert(WorkSubjectCrossRef(work1.workId, subject2.subjectName))
            libraryDao.insert(WorkSubjectCrossRef(work1.workId, subject3.subjectName))

            libraryDao.insert(EditionAuthorCrossRef(edition1.editionId, author1.authorId))
            libraryDao.insert(EditionAuthorCrossRef(edition1.editionId, author2.authorId))
            libraryDao.insert(EditionAuthorCrossRef(edition1.editionId, author3.authorId))

            libraryDao.insert(EditionCoverCrossRef(edition1.editionId, cover1.coverId))
            libraryDao.insert(EditionCoverCrossRef(edition1.editionId, cover2.coverId))
            libraryDao.insert(EditionCoverCrossRef(edition1.editionId, cover3.coverId))

            libraryDao.insert(AuthorCoverCrossRef(author1.authorId, cover1.coverId))
            libraryDao.insert(AuthorCoverCrossRef(author1.authorId, cover2.coverId))
            libraryDao.insert(AuthorCoverCrossRef(author1.authorId, cover3.coverId))
            libraryDao.insert(AuthorCoverCrossRef(author4.authorId, cover3.coverId))
        }
    }

    @Test
    fun getWorkGivesExpectedResult() {
        runBlocking {
            val expected = Work(
                id = "work1",
                title = "MyWork1",
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
                id = "edition1",
                title = "MyEdition1",
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
                id = "author1",
                name = "Author1",
                bio = "bio1",
                wikipedia = "wiki1",
                entityType = "person",
                photos = flowOf(listOf(cover1, cover2, cover3).map { it.toModel(libraryDao) }),
            )
            val result = localDataSource.getAuthor(author1.authorId).first()
            assertThat(result).isEqualTo(expected)
            assertThat(result.name).isEqualTo(expected.name)
            assertThat(result.bio).isEqualTo(expected.bio)
            assertThat(result.wikipedia).isEqualTo(expected.wikipedia)
            assertThat(result.entityType).isEqualTo(expected.entityType)
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

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.clearAllTables()
        db.close()
    }
}