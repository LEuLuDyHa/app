package com.github.leuludyha.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Library local database.
 */
@TypeConverters(WorkEntityConverter::class)
@Database(
    entities = [
        WorkEntity::class,
        AuthorEntity::class,
        CoverEntity::class,
        SubjectEntity::class,
        EditionEntity::class,
        WorkPrefEntity::class,
        AuthorCoverCrossRef::class,
        WorkAuthorCrossRef::class,
        WorkEditionCrossRef::class,
        WorkCoverCrossRef::class,
        WorkSubjectCrossRef::class,
        EditionAuthorCrossRef::class,
        EditionCoverCrossRef::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class LibraryDatabase : RoomDatabase() {
    abstract fun libraryDao(): LibraryDao
}