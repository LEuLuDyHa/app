package com.github.leuludyha.data.db

import androidx.room.*
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

@Dao
interface LibraryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(work: WorkEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(edition: EditionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(author: AuthorEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cover: CoverEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subject: SubjectEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workPref: WorkPrefEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: WorkAuthorCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: WorkEditionCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: WorkCoverCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: AuthorCoverCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: EditionAuthorCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: EditionCoverCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: WorkSubjectCrossRef)

    @Query("DELETE FROM works where workId = :workId")
    suspend fun deleteWork(workId: String)

    @Query("DELETE FROM authors where authorId = :authorId")
    suspend fun deleteAuthor(authorId: String)

    @Query("DELETE FROM Covers where coverId = :coverId")
    suspend fun deleteCover(coverId: Long)

    @Query("DELETE FROM editions where editionId = :editionId")
    suspend fun deleteEdition(editionId: String)

    @Query("DELETE FROM subjects where subjectName = :subject")
    suspend fun deleteSubject(subject: String)

    @Query("DELETE FROM workPrefs where work = :work")
    suspend fun deleteWorkPref(work: WorkEntity)

    @Query("DELETE FROM AuthorCoverCrossRef where authorId = :authorId")
    suspend fun deleteAuthorCoversForId(authorId: String)

    @Query("DELETE FROM EditionAuthorCrossRef where editionId = :editionId")
    suspend fun deleteEditionAuthorsForId(editionId: String)

    @Query("DELETE FROM EditionCoverCrossRef where editionId = :editionId")
    suspend fun deleteEditionCoversForId(editionId: String)

    @Query("DELETE FROM WorkAuthorCrossRef where workId = :workId")
    suspend fun deleteWorkAuthors(workId: String)

    @Query("DELETE FROM WorkCoverCrossRef where workId = :workId")
    suspend fun deleteWorkCovers(workId: String)

    @Query("DELETE FROM WorkEditionCrossRef where workId = :workId")
    suspend fun deleteWorkEditions(workId: String)

    @Query("DELETE FROM WorkSubjectCrossRef where workId = :workId")
    suspend fun deleteWorkSubjects(workId: String)

    @Query("SELECT * FROM works")
    fun getAllWorks(): Flow<List<WorkEntity>>

    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWork(workId: String): Flow<WorkEntity>

    @Query("SELECT * FROM editions WHERE editionId LIKE :editionId")
    fun getEdition(editionId: String): Flow<EditionEntity>

    @Query("SELECT * FROM editions WHERE (isbn10 LIKE :isbn OR isbn13 LIKE :isbn)")
    fun getEditionByISBN(isbn: String): Flow<EditionEntity>

    @Query("SELECT * FROM authors WHERE authorId LIKE :authorId")
    fun getAuthor(authorId: String): Flow<AuthorEntity>

    @Query("SELECT * FROM covers WHERE coverId LIKE :coverId")
    fun getCover(coverId: Long): Flow<CoverEntity>

    @Query("SELECT * FROM workPrefs WHERE work LIKE :work")
    fun getWorkPref(work: WorkEntity): Flow<WorkPrefEntity>

    fun getWorkPref(workId: String) = getWorkPref(WorkEntity(workId = workId, title = null))

    @Transaction
    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWorkWithAuthors(workId: String): Flow<WorkWithAuthors>

    @Transaction
    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWorkWithEditions(workId: String): Flow<WorkWithEditions>

    @Transaction
    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWorkWithCovers(workId: String): Flow<WorkWithCovers>

    @Transaction
    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWorkWithSubjects(workId: String): Flow<WorkWithSubjects>

    @Transaction
    @Query("SELECT * FROM authors WHERE authorId LIKE :authorId")
    fun getAuthorWithWorks(authorId: String): Flow<AuthorWithWorks>

    @Transaction
    @Query("SELECT * FROM authors WHERE authorId LIKE :authorId")
    fun getAuthorWithCovers(authorId: String): Flow<AuthorWithCovers>

    @Transaction
    @Query("SELECT * FROM editions WHERE editionId LIKE :editionId")
    fun getEditionWithAuthors(editionId: String): Flow<EditionWithAuthors>

    @Transaction
    @Query("SELECT * FROM editions WHERE editionId LIKE :editionId")
    fun getEditionWithWorks(editionId: String): Flow<EditionWithWorks>

    @Transaction
    @Query("SELECT * FROM editions WHERE editionId LIKE :editionId")
    fun getEditionWithCovers(editionId: String): Flow<EditionWithCovers>

    suspend fun insert(work: Work) {
        val workEntity = WorkEntity.from(work)

        val authors = work.authors.firstOrNull()
        authors?.forEach { insert(it) }
        val workAuthorCrossRefs = authors?.map { WorkAuthorCrossRef.from(work, it) }

        val editions = work.editions.firstOrNull()
        editions?.forEach { insert(it) }
        val workEditionCrossRefs = editions?.map { WorkEditionCrossRef.from(work, it) }

        val subjects = work.subjects.firstOrNull()
        subjects?.forEach { insertSubject(it) }
        val workSubjectCrossRefs = subjects?.map { WorkSubjectCrossRef.from(work, it) }

        val covers = work.covers.firstOrNull()
        covers?.forEach { insert(it) }
        val workCoverCrossRefs = covers?.map { WorkCoverCrossRef.from(work, it) }

        insert(workEntity)
        workAuthorCrossRefs?.forEach { insert(it) }
        workEditionCrossRefs?.forEach { insert(it) }
        workSubjectCrossRefs?.forEach { insert(it) }
        workCoverCrossRefs?.forEach { insert(it) }
    }

    suspend fun insert(edition: Edition) {
        val editionEntity = EditionEntity.from(edition)

        val authors = edition.authors.firstOrNull()
        authors?.forEach { insert(it) }
        val editionAuthorCrossRefs = authors?.map { EditionAuthorCrossRef.from(edition, it) }

        val works = edition.works.firstOrNull()
        works?.forEach { insert(it) }
        val editionWorkCrossRefs = works?.map { WorkEditionCrossRef.from(it, edition) }

        val covers = edition.covers.firstOrNull()
        covers?.forEach { insert(it) }
        val editionCoverCrossRefs = covers?.map { EditionCoverCrossRef.from(edition, it) }

        insert(editionEntity)
        editionAuthorCrossRefs?.forEach { insert(it) }
        editionWorkCrossRefs?.forEach { insert(it) }
        editionCoverCrossRefs?.forEach { insert(it) }
    }

    suspend fun insert(author: Author) {
        val authorEntity = AuthorEntity.from(author)

        val works = author.works.firstOrNull()
        works?.forEach { insert(it) }
        val authorWorkCrossRefs = works?.map { WorkAuthorCrossRef.from(it, author) }

        val covers = author.covers.firstOrNull()
        covers?.forEach { insert(it) }
        val authorCoverCrossRefs = covers?.map { AuthorCoverCrossRef.from(author, it) }

        insert(authorEntity)
        authorWorkCrossRefs?.forEach { insert(it) }
        authorCoverCrossRefs?.forEach { insert(it) }
    }

    suspend fun insert(cover: Cover) = insert(CoverEntity(cover.id))

    suspend fun insert(workPref: WorkPreference) {
        insert(workPref.work)
        insert(WorkPrefEntity.from(workPref))
    }

    suspend fun insertSubject(subject: String) = insert(SubjectEntity(subject))

    suspend fun delete(work: Work) {
        deleteWork(work.id)
        deleteWorkAuthors(work.id)
        deleteWorkCovers(work.id)
        deleteWorkSubjects(work.id)
        deleteWorkEditions(work.id)
        deleteWorkPref(WorkEntity.from(work))
    }

    suspend fun delete(author: Author) {
        deleteAuthor(author.id)
        deleteAuthorCoversForId(author.id)
    }

    suspend fun delete(edition: Edition) {
        deleteEdition(edition.id)
        deleteEditionAuthorsForId(edition.id)
        deleteEditionCoversForId(edition.id)
    }

    suspend fun delete(cover: Cover) {
        deleteCover(cover.id)
    }

    suspend fun delete(workPref: WorkPreference) {
        deleteWorkPref(WorkEntity.from(workPref.work))
    }
}
