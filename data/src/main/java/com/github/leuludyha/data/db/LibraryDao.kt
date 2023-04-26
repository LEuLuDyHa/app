package com.github.leuludyha.data.db

import androidx.room.*
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

/**
 * Library local DAO.
 */
@Dao
interface LibraryDao {

    /**
     * Inserts the given [WorkEntity] inside the database. If already present, replaces it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(work: WorkEntity)

    /**
     * Inserts the given [EditionEntity] inside the database. If already present, replaces it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(edition: EditionEntity)

    /**
     * Inserts the given [AuthorEntity] inside the database. If already present, replaces it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(author: AuthorEntity)

    /**
     * Inserts the given [CoverEntity] inside the database. If already present, replaces it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cover: CoverEntity)

    /**
     * Inserts the given [SubjectEntity] inside the database. If already present, replaces it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subject: SubjectEntity)

    /**
     * Inserts the given [WorkPrefEntity] inside the database. If already present, replaces it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workPref: WorkPrefEntity)

    /**
     * Inserts the given [WorkAuthorCrossRef] inside the database. If already present, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: WorkAuthorCrossRef)

    /**
     * Inserts the given [WorkEditionCrossRef] inside the database. If already present, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: WorkEditionCrossRef)

    /**
     * Inserts the given [WorkCoverCrossRef] inside the database. If already present, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: WorkCoverCrossRef)

    /**
     * Inserts the given [AuthorCoverCrossRef] inside the database. If already present, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: AuthorCoverCrossRef)

    /**
     * Inserts the given [EditionAuthorCrossRef] inside the database. If already present, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: EditionAuthorCrossRef)

    /**
     * Inserts the given [EditionCoverCrossRef] inside the database. If already present, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: EditionCoverCrossRef)

    /**
     * Inserts the given [WorkSubjectCrossRef] inside the database. If already present, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: WorkSubjectCrossRef)

    /**
     * If present, deletes the [WorkEntity] with the given [workId] from the database.
     */
    @Query("DELETE FROM works where workId = :workId")
    suspend fun deleteWork(workId: String)

    /**
     * If present, deletes the [AuthorEntity] with the given [authorId] from the database.
     */
    @Query("DELETE FROM authors where authorId = :authorId")
    suspend fun deleteAuthor(authorId: String)

    /**
     * If present, deletes the [CoverEntity] with the given [coverId] from the database.
     */
    @Query("DELETE FROM Covers where coverId = :coverId")
    suspend fun deleteCover(coverId: Long)

    /**
     * If present, deletes the [EditionEntity] with the given [editionId] from the database.
     */
    @Query("DELETE FROM editions where editionId = :editionId")
    suspend fun deleteEdition(editionId: String)

    /**
     * If present, deletes the [SubjectEntity] with the given [subject] from the database.
     */
    @Query("DELETE FROM subjects where subjectName = :subject")
    suspend fun deleteSubject(subject: String)

    /**
     * If present, deletes the [WorkPrefEntity] associated to the given [work] from the database.
     */
    @Query("DELETE FROM workPrefs where work = :work")
    suspend fun deleteWorkPref(work: WorkEntity)

    /**
     * If present, deletes the [AuthorCoverCrossRef] with the given [authorId] from the database.
     */
    @Query("DELETE FROM AuthorCoverCrossRef where authorId = :authorId")
    suspend fun deleteAuthorCoversForId(authorId: String)

    /**
     * If present, deletes the [EditionAuthorCrossRef] with the given [editionId] from the database.
     */
    @Query("DELETE FROM EditionAuthorCrossRef where editionId = :editionId")
    suspend fun deleteEditionAuthorsForId(editionId: String)

    /**
     * If present, deletes the [EditionCoverCrossRef] with the given [editionId] from the database.
     */
    @Query("DELETE FROM EditionCoverCrossRef where editionId = :editionId")
    suspend fun deleteEditionCoversForId(editionId: String)

    /**
     * If present, deletes the [WorkAuthorCrossRef] with the given [workId] from the database.
     */
    @Query("DELETE FROM WorkAuthorCrossRef where workId = :workId")
    suspend fun deleteWorkAuthors(workId: String)

    /**
     * If present, deletes the [WorkCoverCrossRef] with the given [workId] from the database.
     */
    @Query("DELETE FROM WorkCoverCrossRef where workId = :workId")
    suspend fun deleteWorkCovers(workId: String)

    /**
     * If present, deletes the [WorkEditionCrossRef] with the given [workId] from the database.
     */
    @Query("DELETE FROM WorkEditionCrossRef where workId = :workId")
    suspend fun deleteWorkEditions(workId: String)

    /**
     * If present, deletes the [WorkSubjectCrossRef] with the given [workId] from the database.
     */
    @Query("DELETE FROM WorkSubjectCrossRef where workId = :workId")
    suspend fun deleteWorkSubjects(workId: String)

    /**
     * @return a flow containing the [WorkEntity] with the given [workId] from the database, if any
     */
    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWork(workId: String): Flow<WorkEntity>

    /**
     * @return a flow containing the [EditionEntity] with the given [editionId] from the database, if any
     */
    @Query("SELECT * FROM editions WHERE editionId LIKE :editionId")
    fun getEdition(editionId: String): Flow<EditionEntity>

    /**
     * @return a flow containing the [EditionEntity] with the given [isbn] from the database, if any
     */
    @Query("SELECT * FROM editions WHERE (isbn10 LIKE :isbn OR isbn13 LIKE :isbn)")
    fun getEditionByISBN(isbn: String): Flow<EditionEntity>

    /**
     * @return a flow containing the [AuthorEntity] with the given [authorId] from the database, if any
     */
    @Query("SELECT * FROM authors WHERE authorId LIKE :authorId")
    fun getAuthor(authorId: String): Flow<AuthorEntity>

    /**
     * @return a flow containing the [CoverEntity] with the given [coverId] from the database, if any
     */
    @Query("SELECT * FROM covers WHERE coverId LIKE :coverId")
    fun getCover(coverId: Long): Flow<CoverEntity>

    /**
     * @return a flow containing the [WorkPrefEntity] attached to the given [work] from the database, if any
     */
    @Query("SELECT * FROM workPrefs WHERE work LIKE :work")
    fun getWorkPref(work: WorkEntity): Flow<WorkPrefEntity>


    /**
     * @return a flow containing the [WorkPrefEntity] attached to the [WorkEntity] with the given [workId] from the database, if any
     */
    fun getWorkPref(workId: String) = getWorkPref(WorkEntity(workId = workId, title = null))

    /**
     * @return a flow containing the list of all the [WorkPrefEntity] in the database
     */
    @Query("SELECT * FROM workPrefs")
    fun getAllWorkPrefs(): Flow<List<WorkPrefEntity>>

    /**
     * @return a flow containing the [WorkWithAuthors] attached to the given [workId], if any
     */
    @Transaction
    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWorkWithAuthors(workId: String): Flow<WorkWithAuthors>

    /**
     * @return a flow containing the [WorkWithEditions] attached to the given [workId], if any
     */
    @Transaction
    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWorkWithEditions(workId: String): Flow<WorkWithEditions>

    /**
     * @return a flow containing the [WorkWithCovers] attached to the given [workId], if any
     */
    @Transaction
    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWorkWithCovers(workId: String): Flow<WorkWithCovers>

    /**
     * @return a flow containing the [WorkWithSubjects] attached to the given [workId], if any
     */
    @Transaction
    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWorkWithSubjects(workId: String): Flow<WorkWithSubjects>

    /**
     * @return a flow containing the [AuthorWithWorks] attached to the given [authorId], if any
     */
    @Transaction
    @Query("SELECT * FROM authors WHERE authorId LIKE :authorId")
    fun getAuthorWithWorks(authorId: String): Flow<AuthorWithWorks>

    /**
     * @return a flow containing the [AuthorWithCovers] attached to the given [authorId], if any
     */
    @Transaction
    @Query("SELECT * FROM authors WHERE authorId LIKE :authorId")
    fun getAuthorWithCovers(authorId: String): Flow<AuthorWithCovers>

    /**
     * @return a flow containing the [EditionWithAuthors] attached to the given [editionId], if any
     */
    @Transaction
    @Query("SELECT * FROM editions WHERE editionId LIKE :editionId")
    fun getEditionWithAuthors(editionId: String): Flow<EditionWithAuthors>

    /**
     * @return a flow containing the [EditionWithWorks] attached to the given [editionId], if any
     */
    @Transaction
    @Query("SELECT * FROM editions WHERE editionId LIKE :editionId")
    fun getEditionWithWorks(editionId: String): Flow<EditionWithWorks>

    /**
     * @return a flow containing the [EditionWithCovers] attached to the given [editionId], if any
     */
    @Transaction
    @Query("SELECT * FROM editions WHERE editionId LIKE :editionId")
    fun getEditionWithCovers(editionId: String): Flow<EditionWithCovers>

    /**
     * Inserts the given [Work] in the database.
     *
     * It includes inserting the corresponding [WorkEntity], all its [Cover]s and `Subject`s
     * and – if [recursive] – all its authors and editions.
     */
    suspend fun insert(work: Work, recursive: Boolean = true) {
        if(recursive) {
            val authors = work.authors.firstOrNull()
            authors?.forEach { insert(it, false) }
            val workAuthorCrossRefs = authors?.map { WorkAuthorCrossRef.from(work, it) }

            val editions = work.editions.firstOrNull()
            editions?.forEach { insert(it, false) }
            val workEditionCrossRefs = editions?.map { WorkEditionCrossRef.from(work, it) }

            workAuthorCrossRefs?.forEach { insert(it) }
            workEditionCrossRefs?.forEach { insert(it) }
        }

        val workEntity = WorkEntity.from(work)
        insert(workEntity)

        val subjects = work.subjects.firstOrNull()
        subjects?.forEach { insertSubject(it) }
        val workSubjectCrossRefs = subjects?.map { WorkSubjectCrossRef.from(work, it) }
        workSubjectCrossRefs?.forEach { insert(it) }

        val covers = work.covers.firstOrNull()
        covers?.forEach { insert(it) }
        val workCoverCrossRefs = covers?.map { WorkCoverCrossRef.from(work, it) }
        workCoverCrossRefs?.forEach { insert(it) }
    }

    /**
     * Inserts the given [Edition] in the database.
     *
     * It includes inserting the corresponding [EditionEntity], all its [Cover]s
     * and – if [recursive] – all its authors and works.
     */
    suspend fun insert(edition: Edition, recursive: Boolean = true) {
        if (recursive) {
            val authors = edition.authors.firstOrNull()
            authors?.forEach { insert(it, false) }
            val editionAuthorCrossRefs = authors?.map { EditionAuthorCrossRef.from(edition, it) }

            val works = edition.works.firstOrNull()
            works?.forEach { insert(it, false) }
            val editionWorkCrossRefs = works?.map { WorkEditionCrossRef.from(it, edition) }

            editionAuthorCrossRefs?.forEach { insert(it) }
            editionWorkCrossRefs?.forEach { insert(it) }
        }

        val editionEntity = EditionEntity.from(edition)
        insert(editionEntity)

        val covers = edition.covers.firstOrNull()
        covers?.forEach { insert(it) }
        val editionCoverCrossRefs = covers?.map { EditionCoverCrossRef.from(edition, it) }
        editionCoverCrossRefs?.forEach { insert(it) }
    }

    /**
     * Inserts the given [Author] in the database.
     *
     * It includes inserting the corresponding [AuthorEntity], all its [Cover]s
     * and – if [recursive] – all its works.
     */
    suspend fun insert(author: Author, recursive: Boolean = true) {
        if (recursive) {
            val works = author.works.firstOrNull()
            works?.forEach { insert(it) }
            val authorWorkCrossRefs = works?.map { WorkAuthorCrossRef.from(it, author) }

            authorWorkCrossRefs?.forEach { insert(it) }
        }
        val authorEntity = AuthorEntity.from(author)
        insert(authorEntity)

        val covers = author.covers.firstOrNull()
        covers?.forEach { insert(it) }
        val authorCoverCrossRefs = covers?.map { AuthorCoverCrossRef.from(author, it) }
        authorCoverCrossRefs?.forEach { insert(it) }
    }

    /**
     * Inserts the given [Cover] in the database.
     *
     * It only includes inserting the corresponding [CoverEntity]
     */
    suspend fun insert(cover: Cover) = insert(CoverEntity(cover.id))

    /**
     * Inserts the given [WorkPreference] in the database.
     *
     * It includes inserting the corresponding [WorkPrefEntity] and – if [recursive] – its work.
     */
    suspend fun insert(workPref: WorkPreference, recursive: Boolean = true) {
        if(recursive) {
            insert(workPref.work, false)
        }

        insert(WorkPrefEntity.from(workPref))
    }

    /**
     * Inserts the given [subject] in the database.
     *
     * It includes inserting the corresponding [SubjectEntity]
     */
    suspend fun insertSubject(subject: String) = insert(SubjectEntity(subject))

    /**
     * Deletes the given [Work] from the database.
     *
     * It includes deleting the corresponding [WorkEntity], [WorkAuthorCrossRef],
     * [WorkCoverCrossRef], [WorkEditionCrossRef], [WorkSubjectCrossRef] and [WorkPrefEntity]
     */
    suspend fun delete(work: Work) {
        deleteWork(work.id)
        deleteWorkAuthors(work.id)
        deleteWorkCovers(work.id)
        deleteWorkSubjects(work.id)
        deleteWorkEditions(work.id)
        deleteWorkPref(WorkEntity.from(work))
    }

    /**
     * Deletes the given [Author] from the database.
     *
     * It includes deleting the corresponding [AuthorEntity] and [AuthorCoverCrossRef]
     */
    suspend fun delete(author: Author) {
        deleteAuthor(author.id)
        deleteAuthorCoversForId(author.id)
    }

    /**
     * Deletes the given [Edition] from the database.
     *
     * It includes deleting the corresponding [EditionEntity], [EditionAuthorCrossRef] and [EditionCoverCrossRef]
     */
    suspend fun delete(edition: Edition) {
        deleteEdition(edition.id)
        deleteEditionAuthorsForId(edition.id)
        deleteEditionCoversForId(edition.id)
    }

    /**
     * Deletes the given [Cover] from the database.
     *
     * It includes deleting the corresponding [CoverEntity]
     */
    suspend fun delete(cover: Cover) {
        deleteCover(cover.id)
    }

    /**
     * Deletes the given [WorkPreference] from the database.
     *
     * It includes deleting the corresponding [WorkPrefEntity]
     */
    suspend fun delete(workPref: WorkPreference) {
        deleteWorkPref(WorkEntity.from(workPref.work))
    }
}
