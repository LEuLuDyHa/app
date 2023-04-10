package com.github.leuludyha.data.db

import androidx.room.*
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Work
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

    @Query("DELETE FROM works")
    suspend fun deleteAllWorks()

    @Query("DELETE FROM authors")
    suspend fun deleteAllAuthors()

    @Query("DELETE FROM Covers")
    suspend fun deleteAllCovers()

    @Query("DELETE FROM editions")
    suspend fun deleteAllEditions()

    @Query("DELETE FROM subjects")
    suspend fun deleteAllSubjects()

    @Query("DELETE FROM AuthorCoverCrossRef")
    suspend fun deleteAllAuthorCoverCrossRefs()

    @Query("DELETE FROM EditionAuthorCrossRef")
    suspend fun deleteAllEditionAuthorCrossRefs()

    @Query("DELETE FROM EditionCoverCrossRef")
    suspend fun deleteAllEditionCoverCrossRefs()

    @Query("DELETE FROM WorkAuthorCrossRef")
    suspend fun deleteAllWorkAuthorCrossRefs()

    @Query("DELETE FROM WorkCoverCrossRef")
    suspend fun deleteAllWorkCoverCrossRefs()

    @Query("DELETE FROM WorkEditionCrossRef")
    suspend fun deleteAllWorkEditionCrossRefs()

    @Query("DELETE FROM WorkSubjectCrossRef")
    suspend fun deleteAllWorkSubjectCrossRefs()

    @Query("SELECT * FROM works")
    fun getAllWorks(): Flow<List<WorkEntity>>

    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWork(workId: String): Flow<WorkEntity>

    @Query("SELECT * FROM editions WHERE editionId LIKE :editionId")
    fun getEdition(editionId: String): Flow<EditionEntity>

    @Query("SELECT * FROM authors WHERE authorId LIKE :authorId")
    fun getAuthor(authorId: String): Flow<AuthorEntity>

    @Query("SELECT * FROM covers WHERE coverId LIKE :coverId")
    fun getCover(coverId: Long): Flow<CoverEntity>

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
        val authorEntities = authors?.map{ AuthorEntity.from(it) }
        val workAuthorCrossRefs = authors?.map { WorkAuthorCrossRef.from(work, it) }

        val editions = work.editions.firstOrNull()
        val editionEntities = editions?.map{ EditionEntity.from(it) }
        val workEditionCrossRefs = editions?.map { WorkEditionCrossRef.from(work, it) }

        val subjects = work.subjects.firstOrNull()
        val subjectEntities = subjects?.map{ SubjectEntity.from(it) }
        val workSubjectCrossRefs = subjects?.map { WorkSubjectCrossRef.from(work, it) }

        val covers = work.covers.firstOrNull()
        val coverEntities = covers?.map{ CoverEntity.from(it) }
        val workCoverCrossRefs = covers?.map { WorkCoverCrossRef.from(work, it) }

        insert(workEntity)
        authorEntities?.forEach { insert(it) }
        workAuthorCrossRefs?.forEach { insert(it) }
        editionEntities?.forEach { insert(it) }
        workEditionCrossRefs?.forEach { insert(it) }
        subjectEntities?.forEach { insert(it) }
        workSubjectCrossRefs?.forEach { insert(it) }
        coverEntities?.forEach { insert(it) }
        workCoverCrossRefs?.forEach { insert(it) }
    }

    suspend fun insert(edition: Edition) {
        val editionEntity = EditionEntity.from(edition)

        val authors = edition.authors.firstOrNull()
        val authorEntities = authors?.map{ AuthorEntity.from(it) }
        val editionAuthorCrossRefs = authors?.map { EditionAuthorCrossRef.from(edition, it) }

        val works = edition.works.firstOrNull()
        val workEntities = works?.map{ WorkEntity.from(it) }
        val editionWorkCrossRefs = works?.map { WorkEditionCrossRef.from(it, edition) }

        val covers = edition.covers.firstOrNull()
        val coverEntities = covers?.map{ CoverEntity.from(it) }
        val editionCoverCrossRefs = covers?.map { EditionCoverCrossRef.from(edition, it) }
        
        insert(editionEntity)
        authorEntities?.forEach { insert(it) }
        editionAuthorCrossRefs?.forEach { insert(it) }
        workEntities?.forEach { insert(it) }
        editionWorkCrossRefs?.forEach { insert(it) }
        coverEntities?.forEach { insert(it) }
        editionCoverCrossRefs?.forEach { insert(it) }
    }

    suspend fun insert(author: Author) {
        val authorEntity = AuthorEntity.from(author)

        val works = author.works.firstOrNull()
        val workEntities = works?.map{ WorkEntity.from(it) }
        val authorWorkCrossRefs = works?.map { WorkAuthorCrossRef.from(it, author) }

        val covers = author.photos.firstOrNull()
        val coverEntities = covers?.map{ CoverEntity.from(it) }
        val authorCoverCrossRefs = covers?.map { AuthorCoverCrossRef.from(author, it) }

        insert(authorEntity)
        workEntities?.forEach { insert(it) }
        authorWorkCrossRefs?.forEach { insert(it) }
        coverEntities?.forEach { insert(it) }
        authorCoverCrossRefs?.forEach { insert(it) }
    }
}
