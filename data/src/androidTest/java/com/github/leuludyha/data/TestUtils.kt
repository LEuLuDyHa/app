package com.github.leuludyha.data

import com.github.leuludyha.data.db.*
import kotlinx.coroutines.runBlocking

object TestUtils {
    val work1 = WorkEntity("work1", "MyWork1")
    val work2 = WorkEntity("work2", "MyWork2")
    val work3 = WorkEntity("work3", "MyWork3")
    val author1 = AuthorEntity("author1", "wiki1", "Author1", birthDate = "1", deathDate = null, "person")
    val author2 = AuthorEntity("author2", null, "Author2", birthDate = "2,", deathDate = null, null)
    val author3 = AuthorEntity("author3", null, "Author3", birthDate = "3", deathDate = null, null)
    val author4 = AuthorEntity("author4", null, "Author4", birthDate = "4", deathDate = null, null)
    val edition1 = EditionEntity("edition1", "MyEdition1", isbn13 = "1", isbn10 = "1")
    val edition2 = EditionEntity("edition2", "MyEdition2", isbn13 = "1", isbn10 = "1")
    val edition3 = EditionEntity("edition3", "MyEdition3", isbn13 = "1", isbn10 = "1")
    val edition4 = EditionEntity("edition4", "MyEdition4", isbn13 = "1", isbn10 = "1")
    val cover1 = CoverEntity(1)
    val cover2 = CoverEntity(2)
    val cover3 = CoverEntity(3)
    val subject1 = SubjectEntity("subject1")
    val subject2 = SubjectEntity("subject2")
    val subject3 = SubjectEntity("subject3")

    fun populateDatabase(libraryDao: LibraryDao) {
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
}