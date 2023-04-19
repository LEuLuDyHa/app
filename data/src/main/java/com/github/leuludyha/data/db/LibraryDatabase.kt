package com.github.leuludyha.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

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
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: LibraryDatabase? = null

        fun getDatabase(context: Context): LibraryDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LibraryDatabase::class.java,
                    "library_databse"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}